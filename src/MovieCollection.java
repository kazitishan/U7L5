import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        // adding all the cast members the name the user inputted to a list
        System.out.print("Enter a cast member: ");
        String castMember = scanner.nextLine().toLowerCase();
        ArrayList<String> searchResults = new ArrayList<String>();
        for (int i = 0; i < movies.size(); i++){
            String[] movieCast = movies.get(i).getCast().split("[|]");
            for (String actor : movieCast){
                if (actor.toLowerCase().contains(castMember) && searchResults.contains(actor) == false){
                    searchResults.add(actor);
                }
            }
        }

        // making sure the inputted cast member is valid
        if (searchResults.size() > 0){

            // sorting searchResults by alphabetical order
            Collections.sort(searchResults);

            // printing the results of the search
            for (int i = 0; i < searchResults.size(); i++){
                System.out.println(i + 1 + ". " + searchResults.get(i));
            }
            System.out.println("Which cast member would you like to know more about?");
            System.out.print("Enter Number: ");
            String actorChoice = scanner.nextLine();
            String selectedActor = searchResults.get(Integer.parseInt(actorChoice) - 1);

            // adding all the movies the selectedActor appeared on to a list
            ArrayList<Movie> movieList = new ArrayList<Movie>();
            for (int i = 0; i < movies.size(); i++){
                String movieCast = movies.get(i).getCast();
                if (movieCast.indexOf(selectedActor) >= 0) movieList.add(movies.get(i));
            }

            // sorting movieList to alphabetical order
            sortResults(movieList);

            // printing the movies the actor has been in
            for (int i = 0; i < movieList.size(); i++){
                System.out.println(i + 1 + ". " + movieList.get(i).getTitle());
            }

            System.out.println("Which movie would you like to know more about?");
            System.out.print("Enter a number: ");
            String movieChoice = scanner.nextLine();
            displayMovieInfo(movieList.get(Integer.parseInt(movieChoice) - 1));
        }
        else{
            System.out.println("There are no cast members with the name " + castMember);
        }

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getKeywords();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int movieChoice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(movieChoice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        // finding all genres
        ArrayList<String> genres = new ArrayList<String>();
        for (int i = 0; i < movies.size(); i++){
            String[] currentGenres = movies.get(i).getGenres().split("[|]");
            for (int k = 0; k < currentGenres.length; k++){
                if (!(genres.contains(currentGenres[k]))) genres.add(currentGenres[k]);
            }
        }

        // sorting genres by alphabetical order
        Collections.sort(genres);

        // printing out all genres
        for (int i = 0; i < genres.size(); i++){
            System.out.println(i + 1 + ". " + genres.get(i));
        }

        // getting the genre the user selected
        System.out.print("Enter the number of a genre: ");
        String genreChoice = scanner.nextLine();
        String selectedGenre = genres.get(Integer.parseInt(genreChoice) - 1);

        // making a list of all the movies in the inputted genre
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        for (int i = 0; i < movies.size(); i++){
            if (movies.get(i).getGenres().indexOf(selectedGenre) >= 0) movieList.add(movies.get(i));
        }

        sortResults(movieList);
        for (int i = 0; i < movieList.size(); i++){
            System.out.println(i + 1 + ". " + movieList.get(i).getTitle());
        }

        System.out.println("Which movie would you like to know more about?");
        System.out.print("Enter a number: ");
        String movieChoice = scanner.nextLine();
        displayMovieInfo(movieList.get(Integer.parseInt(movieChoice) - 1));

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        Movie[] top50 = new Movie[50];
        ArrayList<Movie> allMovies = new ArrayList<Movie>();
        for (int i = 0; i < movies.size(); i++){
            allMovies.add(movies.get(i));
        }

        for (int i = 0; i < 50; i++){
            double highestRating = 0.0;
            int highestRatingIndex = 0;
            Movie highestRatingMovie = new Movie("", "", "", "", "", "", 0, "", 0.0, 0, 0);
            for (int k = 0; k < allMovies.size(); k++){
                if (allMovies.get(k).getUserRating() > highestRating) {
                    highestRating = allMovies.get(k).getUserRating();
                    highestRatingMovie = allMovies.get(k);
                    highestRatingIndex = k;
                }
            }
            allMovies.remove(highestRatingIndex);
            top50[i] = highestRatingMovie;
        }

        for (int i = 0; i < 50; i++){
            System.out.println((i + 1) + ". " + top50[i].getTitle() + ": " + top50[i].getUserRating());
        }

        System.out.println("Which movie would you like to know more about?");
        System.out.print("Enter a number: ");
        String movieChoice = scanner.nextLine();
        displayMovieInfo(top50[Integer.parseInt(movieChoice) - 1]);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue()
    {
        Movie[] top50 = new Movie[50];
        ArrayList<Movie> allMovies = new ArrayList<Movie>();
        for (int i = 0; i < movies.size(); i++){
            allMovies.add(movies.get(i));
        }

        for (int i = 0; i < 50; i++){
            int highestRevenue = 0;
            int highestRevenueIndex = 0;
            Movie highestRevenueMovie = new Movie("", "", "", "", "", "", 0, "", 0.0, 0, 0);
            for (int k = 0; k < allMovies.size(); k++){
                if (allMovies.get(k).getRevenue() > highestRevenue) {
                    highestRevenue = allMovies.get(k).getRevenue();
                    highestRevenueMovie = allMovies.get(k);
                    highestRevenueIndex = k;
                }
            }
            allMovies.remove(highestRevenueIndex);
            top50[i] = highestRevenueMovie;
        }

        for (int i = 0; i < 50; i++){
            System.out.println((i + 1) + ". " + top50[i].getTitle() + ": $" + top50[i].getRevenue());
        }

        System.out.println("Which movie would you like to know more about?");
        System.out.print("Enter a number: ");
        String movieChoice = scanner.nextLine();
        displayMovieInfo(top50[Integer.parseInt(movieChoice) - 1]);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }

}