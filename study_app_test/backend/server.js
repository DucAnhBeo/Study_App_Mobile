const express = require('express');
const mysql = require('mysql2');
const bodyParser = require('body-parser');
const cors = require('cors');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// Database connection
const db = mysql.createConnection({
    host: process.env.DB_HOST || 'localhost',
    user: process.env.DB_USER || 'root',
    password: process.env.DB_PASSWORD || '',
    database: process.env.DB_NAME || 'study_app',
    port: process.env.DB_PORT || 3306
});

// Connect to database
db.connect((err) => {
    if (err) {
        console.error('Database connection failed:', err);
        return;
    }
    console.log('Connected to MySQL database!');
    createUsersTable();
});

// Create users table
function createUsersTable() {
    const createTableQuery = `
        CREATE TABLE IF NOT EXISTS users (
            id INT AUTO_INCREMENT PRIMARY KEY,
            username VARCHAR(50) NOT NULL UNIQUE,
            password VARCHAR(255) NOT NULL,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
        )
    `;

    db.query(createTableQuery, (err, result) => {
        if (err) {
            console.error('Error creating users table:', err);
        } else {
            console.log('Users table created or already exists');
            createDiscussionTables();
        }
    });
}

// Create discussion tables
function createDiscussionTables() {
    const createQuestionsTableQuery = `
        CREATE TABLE IF NOT EXISTS discussion_questions (
            id INT AUTO_INCREMENT PRIMARY KEY,
            user_id INT NOT NULL,
            content TEXT NOT NULL,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
        )
    `;

    const createAnswersTableQuery = `
        CREATE TABLE IF NOT EXISTS discussion_answers (
            id INT AUTO_INCREMENT PRIMARY KEY,
            question_id INT NOT NULL,
            user_id INT NOT NULL,
            content TEXT NOT NULL,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY (question_id) REFERENCES discussion_questions(id) ON DELETE CASCADE,
            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
        )
    `;

    db.query(createQuestionsTableQuery, (err, result) => {
        if (err) {
            console.error('Error creating discussion_questions table:', err);
        } else {
            console.log('Discussion questions table created or already exists');
        }
    });

    db.query(createAnswersTableQuery, (err, result) => {
        if (err) {
            console.error('Error creating discussion_answers table:', err);
        } else {
            console.log('Discussion answers table created or already exists');
        }
    });
}

// Routes

// Register endpoint
app.post('/api/register', (req, res) => {
    const { username, password } = req.body;

    if (!username || !password) {
        return res.status(400).json({
            success: false,
            message: 'Username và password là bắt buộc'
        });
    }

    // Kiểm tra username đã tồn tại chưa
    const checkUserQuery = 'SELECT * FROM users WHERE username = ?';
    db.query(checkUserQuery, [username], (err, results) => {
        if (err) {
            console.error('Database error:', err);
            return res.status(500).json({
                success: false,
                message: 'Lỗi database'
            });
        }

        if (results.length > 0) {
            return res.status(400).json({
                success: false,
                message: 'Username đã tồn tại'
            });
        }

        // Lưu user mới
        const insertUserQuery = 'INSERT INTO users (username, password) VALUES (?, ?)';
        db.query(insertUserQuery, [username, password], (err, result) => {
            if (err) {
                console.error('Database error:', err);
                return res.status(500).json({
                    success: false,
                    message: 'Lỗi khi tạo tài khoản'
                });
            }

            res.status(201).json({
                success: true,
                message: 'Đăng ký thành công',
                userId: result.insertId
            });
        });
    });
});

// Login endpoint
app.post('/api/login', (req, res) => {
    const { username, password } = req.body;

    if (!username || !password) {
        return res.status(400).json({
            success: false,
            message: 'Username và password là bắt buộc'
        });
    }

    // Tìm user trong database
    const findUserQuery = 'SELECT * FROM users WHERE username = ?';
    db.query(findUserQuery, [username], (err, results) => {
        if (err) {
            console.error('Database error:', err);
            return res.status(500).json({
                success: false,
                message: 'Lỗi database'
            });
        }

        if (results.length === 0) {
            return res.status(401).json({
                success: false,
                message: 'Username không tồn tại'
            });
        }

        const user = results[0];

        // So sánh password đơn giản
        if (password === user.password) {
            res.json({
                success: true,
                message: 'Đăng nhập thành công',
                user: {
                    id: user.id,
                    username: user.username,
                    fullName: user.fullName || null
                }
            });
        } else {
            res.status(401).json({
                success: false,
                message: 'Mật khẩu không đúng'
            });
        }
    });
});

// Update profile endpoint
app.put('/api/profile/:userId', (req, res) => {
    const userId = req.params.userId;
    const { username, password } = req.body;

    if (!username || !password) {
        return res.status(400).json({
            success: false,
            message: 'Username và password là bắt buộc'
        });
    }

    // Check if new username already exists (excluding current user)
    const checkUserQuery = 'SELECT * FROM users WHERE username = ? AND id != ?';
    db.query(checkUserQuery, [username, userId], (err, results) => {
        if (err) {
            console.error('Database error:', err);
            return res.status(500).json({
                success: false,
                message: 'Lỗi database'
            });
        }

        if (results.length > 0) {
            return res.status(400).json({
                success: false,
                message: 'Username đã tồn tại'
            });
        }

        // Update user profile
        const updateQuery = 'UPDATE users SET username = ?, password = ? WHERE id = ?';
        db.query(updateQuery, [username, password, userId], (err, result) => {
            if (err) {
                console.error('Database error:', err);
                return res.status(500).json({
                    success: false,
                    message: 'Lỗi khi cập nhật thông tin'
                });
            }

            if (result.affectedRows === 0) {
                return res.status(404).json({
                    success: false,
                    message: 'Không tìm thấy người dùng'
                });
            }

            // Get updated user info
            const getUserQuery = 'SELECT * FROM users WHERE id = ?';
            db.query(getUserQuery, [userId], (err, userResults) => {
                if (err) {
                    console.error('Database error:', err);
                    return res.status(500).json({
                        success: false,
                        message: 'Lỗi khi lấy thông tin người dùng'
                    });
                }

                const updatedUser = userResults[0];
                res.json({
                    success: true,
                    message: 'Cập nhật thông tin thành công',
                    user: {
                        id: updatedUser.id,
                        username: updatedUser.username
                    }
                });
            });
        });
    });
});

// ============ DISCUSSION API ENDPOINTS ============

// Get all questions with answers
app.get('/api/discussion/questions', (req, res) => {
    const getQuestionsQuery = `
        SELECT
            q.id as question_id,
            q.content as question_content,
            q.created_at as question_created_at,
            u1.username as question_author,
            a.id as answer_id,
            a.content as answer_content,
            a.created_at as answer_created_at,
            u2.username as answer_author
        FROM discussion_questions q
        LEFT JOIN users u1 ON q.user_id = u1.id
        LEFT JOIN discussion_answers a ON q.id = a.question_id
        LEFT JOIN users u2 ON a.user_id = u2.id
        ORDER BY q.created_at DESC, a.created_at ASC
    `;

    db.query(getQuestionsQuery, (err, results) => {
        if (err) {
            console.error('Database error:', err);
            return res.status(500).json({
                success: false,
                message: 'Lỗi khi lấy danh sách thảo luận'
            });
        }

        // Group results by question
        const questionsMap = new Map();

        results.forEach(row => {
            if (!questionsMap.has(row.question_id)) {
                questionsMap.set(row.question_id, {
                    id: row.question_id,
                    content: row.question_content,
                    author: row.question_author,
                    created_at: row.question_created_at,
                    answers: []
                });
            }

            if (row.answer_id) {
                questionsMap.get(row.question_id).answers.push({
                    id: row.answer_id,
                    content: row.answer_content,
                    author: row.answer_author,
                    created_at: row.answer_created_at
                });
            }
        });

        const questions = Array.from(questionsMap.values());

        res.json({
            success: true,
            questions: questions
        });
    });
});

// Post a new question
app.post('/api/discussion/questions', (req, res) => {
    const { user_id, content } = req.body;

    if (!user_id || !content) {
        return res.status(400).json({
            success: false,
            message: 'User ID và nội dung câu hỏi là bắt buộc'
        });
    }

    const insertQuestionQuery = 'INSERT INTO discussion_questions (user_id, content) VALUES (?, ?)';
    db.query(insertQuestionQuery, [user_id, content], (err, result) => {
        if (err) {
            console.error('Database error:', err);
            return res.status(500).json({
                success: false,
                message: 'Lỗi khi tạo câu hỏi'
            });
        }

        res.status(201).json({
            success: true,
            message: 'Câu hỏi đã được tạo thành công',
            questionId: result.insertId
        });
    });
});

// Post an answer to a question
app.post('/api/discussion/answers', (req, res) => {
    const { question_id, user_id, content } = req.body;

    if (!question_id || !user_id || !content) {
        return res.status(400).json({
            success: false,
            message: 'Question ID, User ID và nội dung câu trả lời là bắt buộc'
        });
    }

    // Check if question exists
    const checkQuestionQuery = 'SELECT id FROM discussion_questions WHERE id = ?';
    db.query(checkQuestionQuery, [question_id], (err, results) => {
        if (err) {
            console.error('Database error:', err);
            return res.status(500).json({
                success: false,
                message: 'Lỗi database'
            });
        }

        if (results.length === 0) {
            return res.status(404).json({
                success: false,
                message: 'Câu hỏi không tồn tại'
            });
        }

        // Insert answer
        const insertAnswerQuery = 'INSERT INTO discussion_answers (question_id, user_id, content) VALUES (?, ?, ?)';
        db.query(insertAnswerQuery, [question_id, user_id, content], (err, result) => {
            if (err) {
                console.error('Database error:', err);
                return res.status(500).json({
                    success: false,
                    message: 'Lỗi khi tạo câu trả lời'
                });
            }

            res.status(201).json({
                success: true,
                message: 'Câu trả lời đã được tạo thành công',
                answerId: result.insertId
            });
        });
    });
});

// Delete a question (only by author)
app.delete('/api/discussion/questions/:questionId', (req, res) => {
    const questionId = req.params.questionId;
    const userId = req.query.user_id; // Changed from userId to user_id to match database column

    if (!userId) {
        return res.status(400).json({
            success: false,
            message: 'User ID là bắt buộc'
        });
    }

    // Check if user is the author of the question
    const checkAuthorQuery = 'SELECT user_id FROM discussion_questions WHERE id = ?';
    db.query(checkAuthorQuery, [questionId], (err, results) => {
        if (err) {
            console.error('Database error:', err);
            return res.status(500).json({
                success: false,
                message: 'Lỗi database'
            });
        }

        if (results.length === 0) {
            return res.status(404).json({
                success: false,
                message: 'Câu hỏi không tồn tại'
            });
        }

        if (results[0].user_id !== parseInt(userId)) {
            return res.status(403).json({
                success: false,
                message: 'Bạn chỉ có thể xóa câu hỏi của chính mình'
            });
        }

        // Delete question (answers will be deleted automatically due to CASCADE)
        const deleteQuestionQuery = 'DELETE FROM discussion_questions WHERE id = ?';
        db.query(deleteQuestionQuery, [questionId], (err, result) => {
            if (err) {
                console.error('Database error:', err);
                return res.status(500).json({
                    success: false,
                    message: 'Lỗi khi xóa câu hỏi'
                });
            }

            res.json({
                success: true,
                message: 'Câu hỏi đã được xóa thành công'
            });
        });
    });
});

// Error handling middleware
app.use((err, req, res, next) => {
    console.error(err.stack);
    res.status(500).json({
        success: false,
        message: 'Có lỗi xảy ra trên server'
    });
});

// 404 handler
app.use('*', (req, res) => {
    res.status(404).json({
        success: false,
        message: 'API endpoint không tồn tại'
    });
});

// Start server
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
    console.log(`API endpoints:`);
    console.log(`- POST /api/register                    - Đăng ký`);
    console.log(`- POST /api/login                       - Đăng nhập`);
    console.log(`- PUT  /api/profile/:id                 - Cập nhật thông tin`);
    console.log(`- GET  /api/discussion/questions        - Lấy danh sách câu hỏi và câu trả lời`);
    console.log(`- POST /api/discussion/questions        - Tạo câu hỏi mới`);
    console.log(`- POST /api/discussion/answers          - Trả lời câu hỏi`);
    console.log(`- DELETE /api/discussion/questions/:id  - Xóa câu hỏi`);
});

module.exports = app;
