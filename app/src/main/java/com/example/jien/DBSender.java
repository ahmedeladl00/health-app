package com.example.jien;

import android.content.Context;
import android.widget.Toast;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DBSender {
    private static final String API_URL = "https://jien.live/api/";
    private Context context;
    private String dbName;
    private String username;

    public DBSender(Context context, String dbName, String username) {
        this.context = context;
        this.dbName = dbName;
        this.username = username;
    }

    void uploadFile() {
        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create service
        FileUploadService service = retrofit.create(FileUploadService.class);

        // Prepare file to be uploaded
        File file = new File(context.getDatabasePath(this.dbName).getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        // Send the file
        Call<ResponseBody> call = service.uploadFile(getRequestBody(this.username), filePart);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // File upload successful
                    Toast.makeText(context, "File uploaded successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // File upload failed
                    Toast.makeText(context, "File upload failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // File upload failed
                Toast.makeText(context, "File upload failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private RequestBody getRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }
}
