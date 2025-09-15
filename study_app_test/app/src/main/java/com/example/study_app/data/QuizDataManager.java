package com.example.study_app.data;

import com.example.study_app.model.Question;
import com.example.study_app.model.Quiz;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizDataManager {
    private static final Map<String, List<Quiz>> quizzesData = new HashMap<>();

    static {
        initializeQuizzesData();
    }

    private static void initializeQuizzesData() {
        // Lớp 6 - Cánh diều
        List<Quiz> grade6CanhDieu = new ArrayList<>();

        // Quiz 1: Kiến thức cơ bản về chất
        List<Question> quiz1Questions = Arrays.asList(
            new Question("q1", "Chất là gì?",
                Arrays.asList("Thứ tạo thành vật", "Một loại vật", "Một hình dạng", "Một màu sắc"),
                0, "Chất là thứ mà các vật được tạo thành."),
            new Question("q2", "Nước có công thức hóa học là gì?",
                Arrays.asList("H2O", "CO2", "NaCl", "O2"),
                0, "Nước có công thức hóa học là H2O, gồm 2 nguyên tử hydro và 1 nguyên tử oxi."),
            new Question("q3", "Tính chất nào sau đây là tính chất vật lý?",
                Arrays.asList("Khả năng cháy", "Màu sắc", "Khả năng phản ứng", "Khả năng oxi hóa"),
                1, "Màu sắc là tính chất vật lý vì không làm thay đổi bản chất của chất."),
            new Question("q4", "Trong không khí, khí nào chiếm tỷ lệ cao nhất?",
                Arrays.asList("Oxi", "Nitơ", "CO2", "Hydro"),
                1, "Nitơ chiếm khoảng 78% thể tích không khí."),
            new Question("q5", "Hiện tượng nào sau đây là hiện tượng hóa học?",
                Arrays.asList("Nước đóng băng", "Sắt bị gỉ", "Đường tan trong nước", "Nước bay hơi"),
                1, "Sắt bị gỉ là hiện tượng hóa học vì tạo ra chất mới.")
        );
        grade6CanhDieu.add(new Quiz("quiz1_6_cd", "Kiến thức cơ bản về chất",
            "Ôn tập các khái niệm cơ bản về chất và tính chất", "Lớp 6", "Cánh diều", quiz1Questions, 10));

        // Quiz 2: Nước và không khí
        List<Question> quiz2Questions = Arrays.asList(
            new Question("q6", "Nước tinh khiết có tính chất nào?",
                Arrays.asList("Có màu xanh", "Không màu, không mùi", "Có vị ngọt", "Có mùi thơm"),
                1, "Nước tinh khiết không có màu, không có mùi và không có vị."),
            new Question("q7", "Ở bao nhiêu độ C nước sôi ở áp suất bình thường?",
                Arrays.asList("90°C", "95°C", "100°C", "105°C"),
                2, "Nước sôi ở 100°C ở áp suất bình thường (1 atm)."),
            new Question("q8", "Khí oxi trong không khí chiếm bao nhiêu phần trăm?",
                Arrays.asList("78%", "21%", "1%", "50%"),
                1, "Khí oxi chiếm khoảng 21% thể tích không khí."),
            new Question("q9", "Nước đá và nước lỏng khác nhau ở điểm nào?",
                Arrays.asList("Công thức hóa học", "Trạng thái tồn tại", "Thành phần", "Màu sắc"),
                1, "Nước đá và nước lỏng chỉ khác nhau ở trạng thái tồn tại."),
            new Question("q10", "Vai trò quan trọng nhất của nước với sự sống là gì?",
                Arrays.asList("Làm đẹp", "Duy trì sự sống", "Trang trí", "Giải trí"),
                1, "Nước có vai trò quan trọng trong việc duy trì sự sống của mọi sinh vật.")
        );
        grade6CanhDieu.add(new Quiz("quiz2_6_cd", "Nước và không khí",
            "Kiểm tra kiến thức về nước và không khí trong tự nhiên", "Lớp 6", "Cánh diều", quiz2Questions, 10));

        quizzesData.put("Lớp 6_Cánh diều", grade6CanhDieu);

        // Lớp 7 - Cánh diều
        List<Quiz> grade7CanhDieu = new ArrayList<>();

        // Quiz 1: Phản ứng hóa học
        List<Question> quiz3Questions = Arrays.asList(
            new Question("q11", "Dấu hiệu nào chứng tỏ có phản ứng hóa học?",
                Arrays.asList("Thay đổi hình dạng", "Tạo ra chất mới", "Thay đổi kích thước", "Thay đổi vị trí"),
                1, "Phản ứng hóa học luôn tạo ra chất mới có tính chất khác chất ban đầu."),
            new Question("q12", "Trong phương trình: A + B → C + D, chất nào là sản phẩm?",
                Arrays.asList("A và B", "C và D", "A và C", "B và D"),
                1, "C và D là sản phẩm của phản ứng, được tạo thành từ chất tham gia A và B."),
            new Question("q13", "Nguyên tắc cân bằng phương trình hóa học dựa trên?",
                Arrays.asList("Định luật bảo toàn khối lượng", "Tốc độ phản ứng", "Nhiệt độ", "Áp suất"),
                0, "Phương trình hóa học phải tuân theo định luật bảo toàn khối lượng."),
            new Question("q14", "Oxi có tính chất hóa học đặc trưng là gì?",
                Arrays.asList("Hỗ trợ sự cháy", "Không phản ứng", "Cản trở cháy", "Không tan trong nước"),
                0, "Oxi có tính chất hỗ trợ sự cháy và sự hô hấp."),
            new Question("q15", "Phản ứng đốt cháy carbon trong oxi tạo ra chất nào?",
                Arrays.asList("CO", "CO2", "H2O", "O2"),
                1, "Carbon cháy trong oxi tạo ra carbon dioxide (CO2).")
        );
        grade7CanhDieu.add(new Quiz("quiz3_7_cd", "Phản ứng hóa học",
            "Kiểm tra hiểu biết về phản ứng hóa học và phương trình", "Lớp 7", "Cánh diều", quiz3Questions, 15));

        // Quiz 2: Oxi - Không khí
        List<Question> quiz4Questions = Arrays.asList(
            new Question("q16", "Oxi được điều chế trong phòng thí nghiệm bằng cách nào?",
                Arrays.asList("Đun nóng KMnO4", "Làm lạnh không khí", "Pha loãng axit", "Đun nóng nước"),
                0, "Oxi có thể được điều chế bằng cách đun nóng kali permanganat (KMnO4)."),
            new Question("q17", "Để nhận biết khí oxi, ta dùng phương pháp nào?",
                Arrays.asList("Que đóm cháy dở", "Que đóm tắt", "Giấy quỳ", "Nước vôi trong"),
                0, "Oxi làm que đóm cháy dở bùng cháy mạnh."),
            new Question("q18", "Trong h���p chất, oxi thường có hóa trị là?",
                Arrays.asList("I", "II", "III", "IV"),
                1, "Trong hầu hết các hợp chất, oxi có hóa trị II."),
            new Question("q19", "Phản ứng nào sau đây là phản ứng oxi hóa?",
                Arrays.asList("NaCl → Na + Cl", "2Mg + O2 → 2MgO", "H2O → H2 + O", "CO2 → C + O2"),
                1, "Magiê kết hợp với oxi tạo thành magiê oxit là phản ứng oxi hóa."),
            new Question("q20", "Tại sao oxi cần thiết cho sự sống?",
                Arrays.asList("Để thở", "Để ăn", "Để ngủ", "Để chơi"),
                0, "Oxi cần thiết cho quá trình hô hấp của các sinh vật sống.")
        );
        grade7CanhDieu.add(new Quiz("quiz4_7_cd", "Oxi - Không khí",
            "Đánh giá kiến thức về oxi và vai trò trong không khí", "Lớp 7", "Cánh diều", quiz4Questions, 15));

        quizzesData.put("Lớp 7_Cánh diều", grade7CanhDieu);

        // Lớp 8 - Cánh diều
        List<Quiz> grade8CanhDieu = new ArrayList<>();

        // Quiz 1: Nguyên tử và nguyên tố
        List<Question> quiz5Questions = Arrays.asList(
            new Question("q21", "Nguyên tử gồm những hạt cơ bản nào?",
                Arrays.asList("Proton, electron", "Proton, neutron, electron", "Chỉ có electron", "Proton, neutron"),
                1, "Nguyên tử gồm 3 hạt cơ bản: proton, neutron (trong hạt nhân) và electron (ở vỏ)."),
            new Question("q22", "Số proton trong nguyên tử bằng với?",
                Arrays.asList("Số neutron", "Số electron", "Số khối", "Không bằng gì"),
                1, "Trong nguyên tử trung hòa điện, số proton bằng số electron."),
            new Question("q23", "Nguyên tố được đặc trưng bởi?",
                Arrays.asList("Số neutron", "Số electron", "Số proton", "Khối lượng"),
                2, "Số proton (hay số hiệu nguyên tử) đặc trưng cho nguyên tố hóa học."),
            new Question("q24", "Trong bảng tuần hoàn, các nguyên tố được sắp xếp theo?",
                Arrays.asList("Khối lượng tăng dần", "Số proton tăng dần", "Tên nguyên tố", "Màu sắc"),
                1, "Các nguyên tố trong bảng tuần hoàn được sắp xếp theo số proton tăng dần."),
            new Question("q25", "Đơn chất là gì?",
                Arrays.asList("Chất gồm nhiều nguyên tố", "Chất gồm một nguyên tố", "Chất có màu", "Chất lỏng"),
                1, "Đơn chất là những chất được tạo thành từ một nguyên tố hóa học.")
        );
        grade8CanhDieu.add(new Quiz("quiz5_8_cd", "Nguyên tử và nguyên tố",
            "Kiểm tra kiến thức về cấu tạo nguyên tử và nguyên tố", "Lớp 8", "Cánh diều", quiz5Questions, 20));

        // Quiz 2: Đơn chất và hợp chất
        List<Question> quiz6Questions = Arrays.asList(
            new Question("q26", "Hợp chất là chất được tạo thành từ?",
                Arrays.asList("Một nguyên tố", "Hai nguyên tố trở lên", "Ba nguyên tố", "Bốn nguyên tố"),
                1, "Hợp chất được tạo thành từ hai hay nhiều nguyên tố hóa học khác nhau."),
            new Question("q27", "H2O là đơn chất hay hợp chất?",
                Arrays.asList("Đơn chất", "Hợp chất", "Không phải cả hai", "Tùy điều kiện"),
                1, "H2O là hợp chất vì được tạo thành từ hai nguyên tố H và O."),
            new Question("q28", "O2 thuộc loại chất nào?",
                Arrays.asList("Hợp chất", "Đơn chất", "Hỗn hợp", "Dung dịch"),
                1, "O2 là đơn chất vì được tạo thành từ một nguyên tố oxi."),
            new Question("q29", "Công thức hóa học cho biết điều gì?",
                Arrays.asList("Màu sắc chất", "Thành phần nguyên tố", "Trạng thái", "Mùi vị"),
                1, "Công thức hóa học cho biết thành phần nguyên tố và số lượng nguyên tử của mỗi nguyên tố."),
            new Question("q30", "NaCl là ví dụ của?",
                Arrays.asList("Đơn chất kim loại", "Đơn chất phi kim", "Hợp chất", "Hỗn hợp"),
                2, "NaCl (muối ăn) là hợp chất được tạo thành từ natri và clo.")
        );
        grade8CanhDieu.add(new Quiz("quiz6_8_cd", "Đơn chất và hợp chất",
            "Phân biệt đơn chất và hợp chất, công thức hóa học", "Lớp 8", "Cánh diều", quiz6Questions, 20));

        quizzesData.put("Lớp 8_Cánh diều", grade8CanhDieu);
    }

    public static List<Quiz> getQuizzes(String grade, String textbook) {
        String key = grade + "_" + textbook;
        return quizzesData.getOrDefault(key, new ArrayList<>());
    }

    public static Quiz getQuizById(String quizId) {
        for (List<Quiz> quizList : quizzesData.values()) {
            for (Quiz quiz : quizList) {
                if (quiz.getId().equals(quizId)) {
                    return quiz;
                }
            }
        }
        return null;
    }
}
