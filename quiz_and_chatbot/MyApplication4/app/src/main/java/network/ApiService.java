package network;

import java.util.List;
import model.Question;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("questions")
    Call<List<Question>> getQuestions();
}