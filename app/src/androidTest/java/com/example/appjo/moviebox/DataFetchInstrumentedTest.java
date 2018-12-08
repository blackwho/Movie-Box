package com.example.appjo.moviebox;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.appjo.moviebox.Models.MovieModel;
import com.example.appjo.moviebox.Repos.MovieRepository;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DataFetchInstrumentedTest {
    private Context context;
    private LiveData<List<MovieModel>> movies;
    private String url = "https://api.themoviedb.org/3/trending/all/day?api_key=4d68386fdfbb718a8c36cc1c1053c82e";
    private CountDownLatch signal = new CountDownLatch(1);

    @Test
    public void initialCheckTest(){
        assertEquals(3,3);
    }

    @Test
    public void dashboardDataFetchTest(){
        context = InstrumentationRegistry.getTargetContext();
        movies = MovieRepository.getInstanceTest(context).getMovies(url);
        try {
            signal.await(5, TimeUnit.SECONDS);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        assertEquals(movies.getValue().size(), 20);
    }
}
