package com.vttp2022.shoppingcart.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
// import java.nio.file.Files;
// import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component ("cart")
public class Cart {

    @Autowired
    ApplicationArguments appArgs;

    //initialise cart
    public List<String> cartList = new LinkedList<>();
    public File cartFile;
    public FileInputStream fis;
    public static String cartDb = "C:/Users/lowke/Desktop/NUSISS/ssfWorkshop/shoppingcart/data";
    // public String cartDb = appArgs.getOptionValues("dataDir").get(0);
    public static File cartDbFolder = new File(cartDb);
    public String username;
    public String itemToAdd;


    // //create directory
    // public static void createDb(String cartDb){
    //     try{
    //         // define the new file
    //         cartDbFolder = new File(cartDb);
    //         //get the path of the file and store it into path object
    //         Path path = cartDbFolder.toPath();
    //         //create directories only take in path objects (create folder)
    //         Files.createDirectories(path);
    //         System.out.println(cartDbFolder.getPath());
    //     } catch(IOException e){
    //         e.printStackTrace();
    //     }

    // }
  

    public String getItemToAdd() {
        return itemToAdd;
    }



    public void setItemToAdd(String itemToAdd) {
        this.itemToAdd = itemToAdd;
    }



    public String getUsername() {
        return username;
    }



    public void setUsername(String username) {
        this.username = username;
    }



    public List<String> getCartList() {
        return cartList;
    }
    
    

    //load & create cart

    public void loadCart(){
        // path\\user
        String saveLocation =  cartDbFolder.getPath() + File.separator + username +".txt";
        //define new file at location specified
        cartFile = new File(saveLocation);
        System.out.println(cartFile.toPath());

        //if file exists, read from file and put it into a list and return
        if (cartFile.exists()){
            try {
                //Inputstream can be used insteead of FileInputstream as well
                fis = new FileInputStream(cartFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //BufferedReader can us used instead of Scanner
            //will need to pass InputStreamReader into BufferedReader
            Scanner scanner = new Scanner(fis);
            while (scanner.hasNextLine()) {
                cartList.add(scanner.nextLine());                
                }
                scanner.close();
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
               
            } else {
                //if file does not exist, return an empty list
                System.out.println("User does not exist!");
                System.out.println("New shopping cart iniitiated");
                
            }

        }
    
    //write to Cart
    public void saveCart(){

        //define new file object same as above step in load function
        String saveLocation =  cartDbFolder.getPath() + File.separator + username +".txt";
        cartFile = new File(saveLocation);
    
        try {
            PrintWriter printWriter = new PrintWriter(cartFile);
            BufferedWriter bw = new BufferedWriter(printWriter);

            for (String item:cartList)
            bw.write(item+"\n");

            bw.flush();
            bw.close();
            printWriter.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }

        }

        public void addItem(){
            Boolean itemFound=false;
                for (int i = 0; i < cartList.size(); i++) {
                    if (cartList.get(i).contains(itemToAdd)){
                        String[]splitItem = cartList.get(i).split(":");
                        Integer quantity = Integer.parseInt(splitItem[0]);
                        quantity++;
                        String combinedItem = quantity + ":" + itemToAdd;
                        cartList.set(i, combinedItem);
                        itemFound=true;
                    } 
                }
                if (!itemFound){
                    System.out.println(itemToAdd+ "added");
                    cartList.add("1:"+itemToAdd);}
            }
                
        public void delItem(String itemDel){
            String[]splitItem = itemDel.split(":");
            String itemToDelete = splitItem[1];
            for (int i = 0; i < cartList.size(); i++) {
                if(cartList.get(i).contains(itemToDelete))
                cartList.remove(i);
            }
        }

        public void shiftItem (String item){
            for (int i = 0; i < cartList.size(); i++) {
                if(cartList.get(i).equals(item)){
                    cartList.remove(i);
                    cartList.add(i-1, item);
                    break;
                }


        }
    }

    public void change(String itemToBeChanged){
        Integer quantity=0;
        Boolean itemFound=false;
        int itemIndex=0;
        String itemString="";
        int originalIndex=0;

        //check if item is already in the list    
        for (int j = 0; j < cartList.size(); j++) {
            //if item is already in the list,
            if (cartList.get(j).contains(itemToAdd)){
                //find quantity of original itemToBeChanged
                String[]splitItemToAdd = cartList.get(j).split(":");
                quantity = Integer.parseInt(splitItemToAdd[0]) ;
                itemFound = true;
                itemIndex =j;
            }
        }
        System.out.println(quantity);
        System.out.println(itemIndex);

        //find the item to be changed
        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).contains(itemToBeChanged)){
                String[]splitItem = cartList.get(i).split(":");
                quantity += Integer.parseInt(splitItem[0]);}
                itemString = quantity + ":" + itemToAdd;
                originalIndex = i;
                }

        if (!itemFound){
            cartList.set(originalIndex, itemString);
            
        } else {
            cartList.set(itemIndex, itemString);
            cartList.remove(originalIndex);
        }
    }
           
}
    

