package com.vttp2022.shoppingcart.controller;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vttp2022.shoppingcart.model.Cart;

@Controller
public class StoreController {

  
    Cart cartObj;
    String currentUser;
    String tempItem;



    private static final Logger logger = LoggerFactory.getLogger(StoreController.class);

    @GetMapping(path={"shoppingcart","/"})
    public String showStore(@ModelAttribute Cart cartObj, Model model){
        model.addAttribute("cartObj",cartObj);
        return "storefront";
    }

    @RequestMapping(path="login")
    public String login(@ModelAttribute Cart cartObj, Model model){
        currentUser = cartObj.getUsername();
        logger.info("current user is " + currentUser);
        cartObj.loadCart();
        logger.info(cartObj.getCartList().toString());
        model.addAttribute("cartObj",cartObj);
        return "storefront";
    }

    @RequestMapping(path="add")
    public String add(@ModelAttribute Cart cartObj, Model model){
        logger.info("current user is " + cartObj.getUsername());
        cartObj.setUsername(currentUser);
        logger.info("current user is " + cartObj.getUsername());
        cartObj.loadCart();
        logger.info(cartObj.getCartList().toString());
        logger.info("item to add "+ cartObj.getItemToAdd());
        cartObj.addItem();
        logger.info(cartObj.getCartList().toString());
        cartObj.saveCart();
        model.addAttribute("cartObj",cartObj);

        return "storefront";
    }

    @RequestMapping(path="del/{item}")
    public String del(
        @PathVariable(name="item", required=true) String item,
        @ModelAttribute Cart cartObj, Model model
    ){
        cartObj.setUsername(currentUser);
        logger.info("current user is " + cartObj.getUsername());
        cartObj.loadCart();
        cartObj.delItem(item);
        cartObj.saveCart();
        model.addAttribute("cartObj",cartObj);

        return"storefront";
    }

    @RequestMapping(path="shift/{item}")
    public String shift(
        @PathVariable(name="item", required=true) String item,
        @ModelAttribute Cart cartObj, Model model
    ){
        cartObj.setUsername(currentUser);
        logger.info("current user is " + cartObj.getUsername());
        cartObj.loadCart();
        cartObj.shiftItem(item);
        cartObj.saveCart();
        model.addAttribute("cartObj",cartObj);

        return"storefront";
    }

    @RequestMapping(path="edit/{item}")
    public String edit(
        @PathVariable(name="item", required=true) String item,
        @ModelAttribute Cart cartObj, Model model
    ){
        cartObj.setUsername(currentUser);
        logger.info("current user is " + cartObj.getUsername());
        cartObj.loadCart();
        String[]splitItem = item.split(":");
        tempItem = splitItem[1];
        cartObj.setItemToAdd(tempItem);
        model.addAttribute("cartObj",cartObj);

        return"storefrontMirror";
    }
    
    @RequestMapping(path="change")
    public String edit(@ModelAttribute Cart cartObj, Model model){
        logger.info("current user is " + cartObj.getUsername());
        cartObj.setUsername(currentUser);
        logger.info("current user is " + cartObj.getUsername());
        cartObj.loadCart();
        logger.info(cartObj.getCartList().toString());
        logger.info("item to add "+ cartObj.getItemToAdd());
        cartObj.change(tempItem);
        logger.info(cartObj.getCartList().toString());
        cartObj.saveCart();
        model.addAttribute("cartObj",cartObj);

        return "storefront";
    }
}
