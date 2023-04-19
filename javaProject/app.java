import java.sql.*;
import java.util.*;
// import java.time.*;
// import java.text.SimpleDateFormat;

public class app extends sqlitehandler{

    public static void addUser(Connection admConnection){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter user ID: ");
        int userid = sc.nextInt();
        System.out.println("Enter name: ");
        String name = sc.next();
        System.out.println("Enter address: ");
        String address = sc.next();
        System.out.println("Enter phone no: ");
        long phoneno = sc.nextLong();
        System.out.println("Enter password: ");
        String password = sc.next();

        //Adding to database named Library_Management
        String sql = "insert into user values("+ userid +", '"+ name +"','"+ address +"',"+ phoneno +",'"+ password +"');";
        //System.out.println(sql);
        runStatement(admConnection,sql);
    sc.close();    
    }

    public static void deleteLibrarianInfo(Connection admConnection){
   Scanner sc=new Scanner(System.in);
   System.out.println("Enter the Librarian's User Id whose details you want to delete");
   int delLib=sc.nextInt();
   sc.close();

   //deleting the user whose employee id is given
   String sql="delete from user where userid = +"+ delLib+ "";
   runStatement(admConnection, sql); 
}



    public static void addBook(Connection admConnection){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter book id: ");
        int bookid = sc.nextInt();
        System.out.println("Enter book name: ");
        String bookname = sc.next();
        System.out.println("Enter author name: ");
        String authorname = sc.next();

        String sql2 = "insert into bookTable values(" + bookid + ", '"+ bookname +"', '"+ authorname +"');";
        runStatement(admConnection, sql2);
    sc.close();    
    }

    public static void bookSearch(Connection admConnection){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter book name: "); //bookid is primary key
        String bname = sc.next();
        sc.close();
        String sql1 = "select * from bookTable where bookname = +'"+ bname +"'";
        ResultSet out = runStatement(admConnection, sql1);
        // if((bname.equals(out))){
        //     System.out.println("Book not found");
        // }else{
            try{
                if(!(out.next())){
                    System.out.println("Book not found");
                }
                else{
                    String sql5 = "insert into issuefrom values('" + out.getInt("bookid") + "', '"+ out.getString("bookname") +"', '"+ out.getString("authorname") +"');"; 
                    System.out.println("BOOK ID----BOOK NAME----AUTHOR NAME");
                    System.out.println("--------------------------------------");
                    System.out.println(out.getInt("bookid")+ "          " +
                                       out.getString("bookname")+ "           " +
                                       out.getString("authorname"));
                }
            }
            catch (SQLException e){
                e.printStackTrace();
                System.out.println("Error occured in SQL statement" + e.getMessage());
            }     
    }

    public static void login(Connection c1){
        Scanner sc = new Scanner(System.in);
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("Enter your choice...");
            int choice = sc.nextInt();
            switch(choice){
                case 1:
                adminLogin(c1);
                break;
                case 2:
                userlogin(c1);
                break;
                default:
                System.out.println("INVALID CHOICE!");
            }
    }


    public static void adminLogin(Connection c1){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = sc.next();
        System.out.println("Enter your password: ");
        String password = sc.next();
        String sql = "select * from user";
        ResultSet out = runStatement(c1,sql);
       try{
            while(out.next()){
                String username1 = out.getString("username");
                String password1 = out.getString("password");
                if(username1.equals(username) && password1.equals(password)){
                    System.out.println("WELCOME" + " "+ username);
                    System.out.println("You have logged in as admin successfully! ");
                    System.out.println();
                    System.out.println("press any key to move further...");
                    sc.next();
                    System.out.println();
                    System.out.println("1. Add User Details");
                    System.out.println("2. Terminate from job ");
                    System.out.println("Enter your choice...");
                    int choice = sc.nextInt();
                    switch(choice){
                        case 1:
                            addUser(c1);
                            break;
                        case 2:
                            deleteLibrarianInfo(c1);
                            break;
                        default:
                            System.out.println("INVALID CHOICE!");
                    }
                    return;
                }
            }
            System.out.println("Incorrect username or password");
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println(e);
        }
    }

 public static void userlogin(Connection c1){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = sc.next();
        System.out.println("Enter your password: ");
        String password = sc.next();
        String sql = "select * from user";
        ResultSet out = runStatement(c1,sql);

       try{
            while(out.next()){
                String username1 = out.getString("username");
                String password1 = out.getString("password");
                if(username1.equals(username) && password1.equals(password)){
                    System.out.println("WELCOME" + " "+ username);
                    System.out.println("You have logged in as admin successfully! ");
                    System.out.println();
                    System.out.println("press any key to move further...");
                    sc.next();
                    System.out.println();
                    System.out.println("1. Add Book Details");
                    System.out.println("2. Search books ");
                    System.out.println("3. Issue books ");
                    System.out.println("4. Return books ");
                    System.out.println("Enter your choice...");
                    int choice = sc.nextInt();
                    switch(choice){
                        case 1:
                            addBook(c1);
                            break;
                        case 2:
                            bookSearch(c1);
                            break;
                        case 3:
                            issueBook(c1);
                            break;
                        case 4:
                            returnBook(c1);
                            break;        
                        default:
                            System.out.println("INVALID CHOICE!");
                            break;
                    }
                    return;
                }
            }
            System.out.println("Incorrect username or password");
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println(e);
        }
    }

//TODO----------------------------------------------ISSUE AND RETURN---------------------------------------------------------
    public static void issueBook(Connection c1) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter book id: ");
        String bookId = sc.next();
        System.out.println("Enter issue date (yyyy-mm-dd): ");
        String issueDate = sc.next();
        System.out.println("Enter due date (yyyy-mm-dd): ");
        String dueDate = sc.next();
    try{
        String check = "Select bookid from bookTable where bookid = '"+ bookId +"' ";
        ResultSet checkit = runStatement(c1,check);
        if(checkit.next()){
            String sql = "INSERT INTO issuedbooks (bookId, bookname, authorname, issueDate, dueDate) SELECT bookId, bookname, authorname, '" + issueDate + "', '" + dueDate + "' FROM bookTable WHERE bookId = '" + bookId + "'";
            String sql1 = "DELETE FROM bookTable WHERE bookid='" + bookId + "'";
            ResultSet result = runStatement(c1, sql);
            runStatement(c1,sql1);
            if (result == null) {
                System.out.println("Book issued successfully!");
            } else {
                System.out.println("Failed to issue book!");
            }
        }else{
            System.out.println("Wrong book id.");
        }
    }
    catch(SQLException e){
        // e.printStackTrace();
        System.out.println("Error occurred while executing SQL statement: " + e.getMessage());
    }
    }

    public static void returnBook(Connection c1) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter book id: ");
    String bookId = sc.next();

    try {
        //fetch bookname and authorname from bookTable using bookId
        String sqlSelect = "select bookname, authorname from issuedbooks where bookid='" + bookId + "'";
        ResultSet rs = runStatement(c1, sqlSelect);
        String bookname = "";
        String authorname = "";
        if (rs.next()) {
            bookname = rs.getString("bookname");
            authorname = rs.getString("authorname");
        }
        String check = "select bookid from issuedbooks where bookid = '"+ bookId +"'";
        ResultSet checkit = runStatement(c1, check);
        if(checkit.next()){
            // insert book details into bookTable
            String sqlInsert = "insert into bookTable(bookid, bookname, authorname) values ('" + bookId + "','" + bookname + "','" + authorname + "')";
            ResultSet insertResult = runStatement(c1, sqlInsert);
            if (insertResult == null) {
                System.out.println("Book details inserted into bookTable!");
            } else {
                System.out.println("Failed to insert book details into bookTable!");
            }
            //delete book from issued_books
            String sqlDelete = "delete from issuedbooks where bookid='" + bookId + "'";
            ResultSet deleteResult = runStatement(c1, sqlDelete);
            if (deleteResult == null) {
                System.out.println("Book returned successfully!");
            } else {
                System.out.println("Failed to return book!");
            }
    }else{
        System.out.println("Wrong book id");
    }
    } catch (SQLException e) {
        System.out.println("Error occurred while executing SQL statement: " + e.getMessage());
    }
}

    public static void main(String args[]){
        Connection c1 =  create_database("Library");
        // Connection c1 =  create_database("library");
        String sql = "CREATE TABLE IF NOT EXISTS login ("  
                    + " username text,"  
                    + " password text NOT NULL"    
                    + ");"; 
        runStatement(c1,sql);

        String sql3 = "CREATE TABLE IF NOT EXISTS user(" 
                + " userid int primary key not null," 
                + " username text,"  
                + " address text NOT NULL,"
                + " phoneno text,"
                + " password text "    
                + ");"; 
        runStatement(c1,sql3); 
        String sql4 = "CREATE TABLE IF NOT EXISTS bookTable(" 
                + " bookid int primary key not null," 
                + " bookname text,"  
                + " authorname text NOT NULL "    
                + ");"; 
        runStatement(c1,sql4);
        String sql6="CREATE TABLE IF NOT EXISTS issuedbooks("
                +"bookid int primary key not null,"
                +"bookname text,"
                +"authorname text,"
                +"issueDate text,"
                +"dueDate text"
                +");";
        ResultSet res =  runStatement(c1,sql6);
       
        //addUser(c1);
        // addBook(c1);
        //bookSearch(c1);
        // deleteLibrarianInfo(c1);
         login(c1);
        // issueBook(c1);  
        // returnBook(c1);             
     }
}