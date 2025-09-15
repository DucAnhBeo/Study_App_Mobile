const bcrypt = require('bcryptjs');

console.log('=== TẠO MẬT KHẨU HASH BCRYPT ===\n');

// Tạo hash cho mật khẩu "123456"
const password = '123456';
const hash = bcrypt.hashSync(password, 10);

console.log(`Mật khẩu gốc: ${password}`);
console.log(`Hash bcrypt: ${hash}`);

// Test verify
const isValid = bcrypt.compareSync(password, hash);
console.log(`Verify test: ${isValid ? 'THÀNH CÔNG' : 'THẤT BẠI'}`);

console.log('\n=== SCRIPT SQL CẬP NHẬT ===');
console.log(`INSERT INTO users (username, password) VALUES ('admin', '${hash}');`);
console.log(`INSERT INTO users (username, password) VALUES ('testuser', '${hash}');`);
