package allDatabase;

import allDao.MovieDao;
import bacsta.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieDatabase implements MovieDao{
    public static List<Movie> movies=new ArrayList<>();

//    @Override
    public boolean addMovie(Movie movie) {
        movies.add(movie);
        return true;
    }

//    @Override
    public boolean updateMovie(Movie movie) {
        int index = -1;
        if (movies.contains(movie)) {
            index = movies.indexOf(movie);
            movies.set(index, movie);
            System.out.println("影片信息更新成功！");
            return true;
        } else {
            System.out.println("未找到该影片");
            return false;
        }
        
    }

//    @Override
    public boolean deleteMovie(Movie movie) {
        movies.remove(movie);
        return true;
    }

//    @Override
    public void allMovie() {
        for(int i=0;i<movies.size();i++){
            System.out.println("---------------------------------------");
            System.out.println("片名:"+movies.get(i).getTitle());
            System.out.println("影片导演:"+movies.get(i).getDirector());
            System.out.println("影片主演:"+movies.get(i).getLeadingRole());
            System.out.println("剧情简介:"+movies.get(i).getSynopsis());
            System.out.println("时长:"+movies.get(i).getDuration());
            System.out.println("---------------------------------------");
        }
    }

    public Movie findMovieByTitle(String targetTitle) {
        for (Movie movie : movies) {
            if(movie.getTitle()==null)
                continue;
            else if (movie.getTitle().equals(targetTitle)) {
                return movie;
            }
        }
        return null;
    }

    public Movie findMovieByDirector(String targetDirector) {
        for (Movie movie : movies) {
            if(movie.getTitle()==null)
                continue;
            else if (movie.getDirector().equals(targetDirector)) {
                return movie;
            }
        }
        return null;
    }
}
