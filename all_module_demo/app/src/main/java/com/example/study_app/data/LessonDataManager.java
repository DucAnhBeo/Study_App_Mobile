package com.example.study_app.data;

import com.example.study_app.model.Lesson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LessonDataManager {
    private static final Map<String, List<Lesson>> lessonsData = new HashMap<>();

    static {
        initializeLessonsData();
    }

    private static void initializeLessonsData() {
        // Lớp 6 - Cánh diều
        List<Lesson> grade6CanhDieu = new ArrayList<>();
        grade6CanhDieu.add(new Lesson(
            "Bài 1: Khái niệm về chất",
            "Tìm hiểu về khái niệm chất, phân biệt chất và vật",
            "https://youtu.be/chemistry_grade6_lesson1",
            "Chất là thứ mà các vật được tạo thành. Ví dụ: nước, sắt, gỗ...",
            15
        ));
        grade6CanhDieu.add(new Lesson(
            "Bài 2: Tính chất của chất",
            "Khám phá các tính chất vật lý và hóa học của chất",
            "https://youtu.be/chemistry_grade6_lesson2",
            "Tính chất vật lý: màu sắc, mùi vị, trạng thái...\nTính chất hóa học: khả năng phản ứng...",
            20
        ));
        grade6CanhDieu.add(new Lesson(
            "Bài 3: Nước trong tự nhiên",
            "Tìm hiểu về nước, tính chất và vai trò của nước",
            "https://youtu.be/chemistry_grade6_lesson3",
            "Nước có công thức H2O. Nước có vai trò quan trọng trong đời sống...",
            18
        ));
        lessonsData.put("Lớp 6_Cánh diều", grade6CanhDieu);

        // Lớp 6 - Chân trời sáng tạo
        List<Lesson> grade6ChanTroi = new ArrayList<>();
        grade6ChanTroi.add(new Lesson(
            "Bài 1: Chất và vật thể",
            "Phân biệt chất và vật thể qua các ví dụ thực tế",
            "https://youtu.be/chemistry_grade6_ct_lesson1",
            "Vật thể được tạo thành từ chất. Cùng một chất có thể tạo ra nhiều vật thể khác nhau...",
            16
        ));
        grade6ChanTroi.add(new Lesson(
            "Bài 2: Không khí",
            "Tìm hiểu về thành phần và tính chất của không khí",
            "https://youtu.be/chemistry_grade6_ct_lesson2",
            "Không khí gồm có: 78% nitơ, 21% oxi, 1% các khí khác...",
            22
        ));
        lessonsData.put("Lớp 6_Chân trời sáng tạo", grade6ChanTroi);

        // Lớp 6 - Kết nối tri thức
        List<Lesson> grade6KetNoi = new ArrayList<>();
        grade6KetNoi.add(new Lesson(
            "Bài 1: Các chất xung quanh ta",
            "Nhận biết các chất thường gặp trong cuộc sống",
            "https://youtu.be/chemistry_grade6_kn_lesson1",
            "Các chất thường gặp: nước, muối, đường, sắt, nhôm...",
            17
        ));
        grade6KetNoi.add(new Lesson(
            "Bài 2: Tính chất của nước",
            "Khám phá các tính chất đặc biệt của nước",
            "https://youtu.be/chemistry_grade6_kn_lesson2",
            "Nước không màu, không mùi, không vị ở điều kiện thường...",
            19
        ));
        lessonsData.put("Lớp 6_Kết nối tri thức", grade6KetNoi);

        // Lớp 7 - Cánh diều
        List<Lesson> grade7CanhDieu = new ArrayList<>();
        grade7CanhDieu.add(new Lesson(
            "Bài 1: Phản ứng hóa học",
            "Hiểu về phản ứng hóa học và dấu hiệu nhận biết",
            "https://youtu.be/chemistry_grade7_lesson1",
            "Phản ứng hóa học là quá trình biến đổi chất này thành chất khác...",
            25
        ));
        grade7CanhDieu.add(new Lesson(
            "Bài 2: Phương trình hóa học",
            "Học cách viết và cân bằng phương trình hóa học",
            "https://youtu.be/chemistry_grade7_lesson2",
            "Phương trình hóa học biểu diễn phản ứng hóa học bằng công thức...",
            30
        ));
        grade7CanhDieu.add(new Lesson(
            "Bài 3: Oxi - Tính chất hóa học",
            "Tìm hiểu tính chất hóa học của oxi",
            "https://youtu.be/chemistry_grade7_lesson3",
            "Oxi là chất khí không màu, không mùi, hỗ trợ sự cháy...",
            28
        ));
        lessonsData.put("Lớp 7_Cánh diều", grade7CanhDieu);

        // Lớp 8 - Cánh diều
        List<Lesson> grade8CanhDieu = new ArrayList<>();
        grade8CanhDieu.add(new Lesson(
            "Bài 1: Nguyên tử",
            "Cấu tạo nguyên tử và các hạt cơ bản",
            "https://youtu.be/chemistry_grade8_lesson1",
            "Nguyên tử gồm hạt nhân (proton + neutron) và vỏ electron...",
            35
        ));
        grade8CanhDieu.add(new Lesson(
            "Bài 2: Nguyên tố hóa học",
            "Khái niệm nguyên tố và bảng tuần hoàn",
            "https://youtu.be/chemistry_grade8_lesson2",
            "Nguyên tố hóa học là tập hợp các nguyên tử có cùng số proton...",
            32
        ));
        grade8CanhDieu.add(new Lesson(
            "Bài 3: Đơn chất và hợp chất",
            "Phân biệt đơn chất và hợp chất",
            "https://youtu.be/chemistry_grade8_lesson3",
            "Đơn chất: gồm một nguyên tố. Hợp chất: gồm hai nguyên tố trở lên...",
            26
        ));
        lessonsData.put("Lớp 8_Cánh diều", grade8CanhDieu);

        // Lớp 9 - Cánh diều
        List<Lesson> grade9CanhDieu = new ArrayList<>();
        grade9CanhDieu.add(new Lesson(
            "Bài 1: Tốc độ phản ứng hóa học",
            "Các yếu tố ảnh hưởng đến tốc độ phản ứng",
            "https://youtu.be/chemistry_grade9_lesson1",
            "Tốc độ phản ứng phụ thuộc vào: nhiệt độ, nồng độ, diện tích tiếp xúc...",
            40
        ));
        grade9CanhDieu.add(new Lesson(
            "Bài 2: Cân bằng hóa học",
            "Hiểu về trạng thái cân bằng trong phản ứng thuận nghịch",
            "https://youtu.be/chemistry_grade9_lesson2",
            "Cân bằng hóa học là trạng thái tốc độ phản ứng thuận = tốc độ phản ứng nghịch...",
            45
        ));
        grade9CanhDieu.add(new Lesson(
            "Bài 3: Dung dịch - pH",
            "Tính chất acid, base và thang pH",
            "https://youtu.be/chemistry_grade9_lesson3",
            "pH < 7: acid, pH = 7: trung tính, pH > 7: base...",
            38
        ));
        lessonsData.put("Lớp 9_Cánh diều", grade9CanhDieu);
    }

    public static List<Lesson> getLessons(String grade, String textbook) {
        String key = grade + "_" + textbook;
        return lessonsData.getOrDefault(key, new ArrayList<>());
    }
}
