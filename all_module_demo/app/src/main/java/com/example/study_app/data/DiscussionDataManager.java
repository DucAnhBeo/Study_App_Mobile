package com.example.study_app.data;

import com.example.study_app.model.Answer;
import com.example.study_app.model.Discussion;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiscussionDataManager {
    private static List<Discussion> discussions = new ArrayList<>();

    static {
        initializeSampleData();
    }

    private static void initializeSampleData() {
        // Câu hỏi 1
        Discussion discussion1 = new Discussion(
            "1",
            "Tại sao nước có thể tồn tại ở cả 3 trạng thái: rắn, lỏng, khí?",
            "Học sinh A",
            new Date()
        );
        discussion1.addAnswer(new Answer("1-1",
            "Vì nước có cấu trúc phân tử đặc biệt, liên kết hydro giữa các phân tử H2O thay đổi theo nhiệt độ.",
            "Giáo viên",
            new Date()));
        discussion1.addAnswer(new Answer("1-2",
            "Khi nhiệt độ thay đổi thì động năng phân tử thay đổi, làm thay đổi trạng thái của nước.",
            "Học sinh B",
            new Date()));

        // Câu hỏi 2
        Discussion discussion2 = new Discussion(
            "2",
            "Làm thế nào để phân biệt phản ứng hóa học và phản ứng vật lý?",
            "H���c sinh C",
            new Date()
        );
        discussion2.addAnswer(new Answer("2-1",
            "Phản ứng hóa học tạo ra chất mới, có sự thay đổi màu sắc, nhiệt độ hoặc tạo khí. Phản ứng vật lý chỉ thay đổi hình dạng, kích thước mà không tạo chất mới.",
            "Giáo viên",
            new Date()));

        // Câu hỏi 3
        Discussion discussion3 = new Discussion(
            "3",
            "Tại sao khi pha nước muối lại có vị mặn nhưng không thấy muối?",
            "Học sinh D",
            new Date()
        );
        discussion3.addAnswer(new Answer("3-1",
            "Muối đã hòa tan hoàn toàn trong nước, tạo thành dung dịch đồng nhất. Các ion Na+ và Cl- phân tán đều trong nước.",
            "Giáo viên",
            new Date()));
        discussion3.addAnswer(new Answer("3-2",
            "Đây là hiện tượng hòa tan, muối bị phân ly thành các ion nhỏ không thể nhìn thấy bằng mắt thường.",
            "Học sinh E",
            new Date()));

        discussions.add(discussion1);
        discussions.add(discussion2);
        discussions.add(discussion3);
    }

    public static List<Discussion> getAllDiscussions() {
        return discussions;
    }

    public static void addDiscussion(Discussion discussion) {
        discussions.add(0, discussion); // Add to top
    }

    public static Discussion findDiscussionById(String id) {
        for (Discussion discussion : discussions) {
            if (discussion.getId().equals(id)) {
                return discussion;
            }
        }
        return null;
    }

    public static void addAnswerToDiscussion(String discussionId, Answer answer) {
        Discussion discussion = findDiscussionById(discussionId);
        if (discussion != null) {
            discussion.addAnswer(answer);
        }
    }
}
