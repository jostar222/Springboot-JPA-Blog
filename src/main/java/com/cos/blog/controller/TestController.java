package com.cos.blog.controller;

import com.cos.blog.dto.ChartTestDto;
import com.cos.blog.dto.ModalTestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/modalTest")
    public String modalTest(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {
        return "/test/modalTest";
    }

    @RequestMapping(value = "/modalTest", method = RequestMethod.POST)
    public void modalTest1(ModelAndView mv, @RequestBody ModalTestDto modalTestDto, HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, String> map = new HashMap<String, String>();

        log.info("jsonData={}, {}, {}", modalTestDto.getTitle(), modalTestDto.getBody(), modalTestDto.getUserId());

        map.put("people", "사람");
        map.put("people", "야구");

        mv.addObject("modalTest", map);
    }

    @RequestMapping("/chartTest")
    public ModelAndView chartTest(ModelAndView mv, ChartTestDto chartTestDto, HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, String> map = new HashMap<String, String>();
        List<String> list1 = new ArrayList<String>();
        List<String> list2 = new ArrayList<String>();

        map.put("year", "1992");
        map.put("num", "100");

        list1.add("1992");
        list1.add("1993");
        list1.add("1994");

        list2.add("50");
        list2.add("100");
        list2.add("150");

        mv.addObject("list1", list1);
        mv.addObject("list2", list2);

        System.out.println("list1 = " + list1);
        System.out.println("list2 = " + list2);
        mv.setViewName("/test/chartTest");
        return mv;
    }

    @RequestMapping(value = "/chartTest", method = RequestMethod.POST)
    public void chartTest1(ModelAndView mv, @RequestBody ChartTestDto chartTestDto, HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, String> map = new HashMap<String, String>();

        log.info("jsonData={}, {}", chartTestDto.getChartDate(), chartTestDto.getChartValue());

        map.put("year", "1992");
        map.put("num", "100");

        mv.addObject("chartData", map);
    }

    @RequestMapping("/excelTest")
    public String excelTest(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {
        return "/test/excelTest";
    }


}
