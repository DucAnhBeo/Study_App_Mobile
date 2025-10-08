const express = require('express');
const mysql = require('mysql2');
const bodyParser = require('body-parser');
const cors = require('cors');
require('dotenv').config();

const app = express();

// Middleware
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// Database connection
const db = mysql.createConnection({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_NAME,
    port: process.env.DB_PORT
});

// Connect to database
db.connect((err) => {
    if (err) {
        console.error('Database connection failed:', err);
        return;
    }
    console.log('Connected to MySQL database!');

    // Create users table if it doesn't exist
    createUsersTable();
});

// Create users table
function createUsersTable() {
    const createTableQuery = `
        CREATE TABLE IF NOT EXISTS users (
            id INT AUTO_INCREMENT PRIMARY KEY,
            username VARCHAR(50) NOT NULL UNIQUE,
            password VARCHAR(255) NOT NULL,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        )
    `;

    db.query(createTableQuery, (err, result) => {
        if (err) {
            console.error('Error creating users table:', err);
        } else {
            console.log('Users table created or already exists');
        }
    });
}

// Routes

// Test route
app.get('/', (req, res) => {
    res.json({ message: 'Study App Backend API is running!' });
});

// Register endpoint
app.post('/api/register', (req, res) => {
    const { username, password } = req.body;

    // Validation
    if (!username || !password) {
        return res.status(400).json({
            success: false,
            message: 'Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu'
        });
    }

    if (username.length < 3) {
        return res.status(400).json({
            success: false,
            message: 'Tên đăng nhập phải có ít nhất 3 ký tự'
        });
    }

    if (password.length < 6) {
        return res.status(400).json({
            success: false,
            message: 'Mật khẩu phải có ít nhất 6 ký tự'
        });
    }

    // Check if username already exists
    db.query('SELECT * FROM users WHERE username = ?', [username], (err, results) => {
        if (err) {
            console.error('Database error:', err);
            return res.status(500).json({
                success: false,
                message: 'Lỗi máy chủ'
            });
        }

        if (results.length > 0) {
            return res.status(409).json({
                success: false,
                message: 'Tên đăng nhập đã tồn tại'
            });
        }

        // Insert new user with plain text password
        db.query(
            'INSERT INTO users (username, password) VALUES (?, ?)',
            [username, password], // Store password as plain text
            (err, result) => {
                if (err) {
                    console.error('Error inserting user:', err);
                    return res.status(500).json({
                        success: false,
                        message: 'Lỗi khi tạo tài khoản'
                    });
                }

                return res.status(201).json({
                    success: true,
                    message: 'Đăng ký thành công! Vui lòng đăng nhập.'
                });
            }
        );
    });
});

// Login endpoint
app.post('/api/login', (req, res) => {
    const { username, password } = req.body;

    // Validation
    if (!username || !password) {
        return res.status(400).json({
            success: false,
            message: 'Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu'
        });
    }

    // Check user in database
    db.query('SELECT * FROM users WHERE username = ?', [username], (err, results) => {
        if (err) {
            console.error('Database error:', err);
            return res.status(400).json({
                success: false,
                message: 'Lỗi máy chủ'
            });
        }
        console.log(password);

        if (results.length === 0) {
            return res.status(500).json({
                success: false,
                message: 'Tên đăng nhập hoặc mật khẩu không đúng'
            });
        }

        const user = results[0];

        // Compare password
        const isPasswordValid = password === user.password;

        if (!isPasswordValid) {
            return res.status(500).json({
                success: false,
                message: 'Tên đăng nhập hoặc mật khẩu không đúng'
            });
        }

        // Login successful
        return res.json({
            success: true,
            message: 'Đăng nhập thành công',
            user: {
                id: user.id,
                username: user.username
            }
        });
    });
});

// Get all users (for testing purposes)
app.get('/api/users', (req, res) => {
    db.query('SELECT id, username, created_at FROM users', (err, results) => {
        if (err) {
            console.error('Database error:', err);
            return res.status(500).json({
                success: false,
                message: 'Lỗi máy chủ'
            });
        }

        res.json({
            success: true,
            users: results
        });
    });
});

// Start server
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
    console.log(`API endpoints:`);
    console.log(`- GET http://localhost:${PORT}/`);
    console.log(`- POST http://localhost:${PORT}/api/register`);
    console.log(`- POST http://localhost:${PORT}/api/login`);
    console.log(`- GET http://localhost:${PORT}/api/users`);
});

module.exports = app;
