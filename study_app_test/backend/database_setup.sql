-- Tạo database cho Study App
CREATE DATABASE IF NOT EXISTS study_app_db;
USE study_app_db;

-- Tạo bảng users
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tạo bảng discussion_questions
CREATE TABLE IF NOT EXISTS discussion_questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tạo bảng discussion_answers
CREATE TABLE IF NOT EXISTS discussion_answers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_id INT NOT NULL,
    user_id INT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (question_id) REFERENCES discussion_questions(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tạo index cho username để tăng tốc độ tìm kiếm
CREATE INDEX idx_username ON users(username);
CREATE INDEX idx_question_id ON discussion_answers(question_id);
CREATE INDEX idx_user_id_questions ON discussion_questions(user_id);
CREATE INDEX idx_user_id_answers ON discussion_answers(user_id);

-- Thêm tài khoản người dùng
INSERT INTO users (username, password) VALUES
('admin', '123456'),
('testuser', '123456'),
('hocsinh_a', '123456'),
('hocsinh_b', '123456'),
('giaovien', '123456');

-- ============ DỮ LIỆU MẪU CHO DISCUSSION ============

-- Thêm 5 câu hỏi mẫu
INSERT INTO discussion_questions (user_id, content) VALUES
(3, 'Tại sao khi ta nhìn vào gương, hình ảnh hiện ra lại bị ngược?'),
(4, 'Làm thế nào để tính diện tích hình tròn khi chỉ biết chu vi?'),
(3, 'Tại sao nước biển lại mặn mà nước sông lại ngọt?'),
(4, 'Phân số 3/4 và 0.75 có giống nhau không? Tại sao?'),
(3, 'Vì sao ban ngày trời sáng mà ban đêm lại tối?');

-- Thêm câu trả lời cho câu hỏi 1 (về gương)
INSERT INTO discussion_answers (question_id, user_id, content) VALUES
(1, 5, 'Khi ánh sáng chiếu vào gương phẳng, nó sẽ phản xạ theo quy luật phản xạ ánh sáng. Ảnh trong gương là ảnh ảo, có kích thước bằng vật nhưng bị đối xứng qua mặt gương.'),
(1, 2, 'Đơn giản là vì gương tạo ra ảnh đối xứng. Nếu bạn giơ tay phải, ảnh trong gương sẽ giơ tay trái.');

-- Thêm câu trả lời cho câu hỏi 2 (về diện tích hình tròn)
INSERT INTO discussion_answers (question_id, user_id, content) VALUES
(2, 5, 'Từ chu vi C, ta tính được bán kính r = C/(2π). Sau đó tính diện tích S = πr². Ví dụ: nếu chu vi = 12π thì r = 6, diện tích = 36π.'),
(2, 1, 'Công thức: Chu vi = 2πr, nên r = Chu vi ÷ (2π). Rồi áp dụng S = πr².');

-- Thêm câu trả lời cho câu hỏi 3 (về nước biển)
INSERT INTO discussion_answers (question_id, user_id, content) VALUES
(3, 5, 'Nước biển mặn vì chứa nhiều muối khoáng, chủ yếu là NaCl. Trong khi nước sông được tạo thành từ nước mưa và nước ngầm nên ít muối hơn.'),
(3, 4, 'Nước biển đã tích tụ muối qua hàng triệu năm từ việc xói mòn đất đá. Nước sông thì liên tục được làm mới bởi nước mưa.');

-- Thêm câu trả lời cho câu hỏi 4 (về phân số)
INSERT INTO discussion_answers (question_id, user_id, content) VALUES
(4, 5, 'Có, 3/4 = 0.75. Đây là hai cách biểu diễn khác nhau của cùng một số. 3 chia 4 = 0.75.'),
(4, 1, 'Phân số và số thập phân chỉ là cách viết khác nhau. 3/4 nghĩa là chia 3 cho 4, kết quả là 0.75.');

-- Thêm câu trả lời cho câu hỏi 5 (về ngày đêm)
INSERT INTO discussion_answers (question_id, user_id, content) VALUES
(5, 5, 'Do Trái Đất tự quay quanh trục của nó. Phần hướng về Mặt Trời sẽ có ban ngày, phần quay ra xa Mặt Trời sẽ có ban đêm.'),
(5, 2, 'Trái Đất quay một vòng hết 24 giờ. Khi nơi ta ở quay về phía Mặt Trời thì sáng, quay ra xa thì tối.');

-- Hiển thị dữ liệu mẫu
SELECT 'Danh sách câu hỏi:' as Info;
SELECT q.id, q.content, u.username as author, q.created_at
FROM discussion_questions q
JOIN users u ON q.user_id = u.id
ORDER BY q.created_at DESC;

SELECT 'Danh sách câu trả lời:' as Info;
SELECT a.id, a.question_id, a.content, u.username as author, a.created_at
FROM discussion_answers a
JOIN users u ON a.user_id = u.id
ORDER BY a.question_id, a.created_at;

-- Hiển thị cấu trúc bảng và dữ liệu
DESCRIBE users;
DESCRIBE discussion_questions;
DESCRIBE discussion_answers;
