package id.primadev.retrofitrecycler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import id.primadev.retrofitrecycler.generator.ServiceGenerator;
import id.primadev.retrofitrecycler.models.ChuckNorrisQuote;
import id.primadev.retrofitrecycler.service.ChuckService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ChuckService chuckService;
    private TextView txtQuote;
    private ImageView imageQuote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtQuote = findViewById(R.id.textView);
        imageQuote = findViewById(R.id.imageView);

        chuckService = ServiceGenerator.createService(ChuckService.class);
        Call<ChuckNorrisQuote> call = chuckService.getQuote();

        call.enqueue(new Callback<ChuckNorrisQuote>() {
            @Override
            public void onResponse(Call<ChuckNorrisQuote> call, Response<ChuckNorrisQuote> response) {
               txtQuote.setText(response.body().getValue());
                Picasso.get().load(response.body().getIconUrl()).into(imageQuote);
            }

            @Override
            public void onFailure(Call<ChuckNorrisQuote> call, Throwable t) {
                txtQuote.setText(t.getMessage());
            }
        });
    }
}
