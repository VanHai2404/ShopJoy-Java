package com.edu.shop.controller.admin;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.edu.shop.domain.Category;
import com.edu.shop.model.CategoryDto;
import com.edu.shop.service.CategoryService;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("form")
	public String add(ModelMap map) {
		map.addAttribute("category",new CategoryDto());
		
		map.addAttribute("category",new CategoryDto());
		return "admin/categories/categories";
	}
	@GetMapping("edit/{categoryId}")
	public ModelAndView edit(ModelMap map,@PathVariable("categoryId") Long categoryId) {
		Optional<Category> opt= categoryService.findById(categoryId);
		CategoryDto dto = new CategoryDto();
		System.out.println("ID: "+categoryId);
		if(opt.isPresent()) {
			Category entity=opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);
			map.addAttribute("category",dto);
			return new ModelAndView("admin/categories/categories",map);
		}
		
		
		map.addAttribute("message","Category is not existed !");
		return new ModelAndView("forward:/admin/categories",map);
		
		
		
		
	}
	
	@GetMapping("delete/{categoryId}")
	public ModelAndView  delete(ModelMap model,@PathVariable("categoryId") Long categoryId) {
	  categoryService.deleteById(categoryId);
	  model.addAttribute("message","Category is delete");
	  
		return new ModelAndView("forward:/admin/categories/list");

	}
	
	@PostMapping("saveorUpdate")
	public ModelAndView saveorUpdate(ModelMap model,CategoryDto categoryDto) {
		Category entity = new Category();
		BeanUtils.copyProperties(categoryDto, entity); // copy DTO bỏ vô entity 
	 
		categoryService.save(entity);
		model.addAttribute("message","Category is Save !");
		return new ModelAndView("forward:/admin/categories", model);
	}
	@RequestMapping("")
	public String list(ModelMap map) {
		System.out.println("Listtt ");
		List<Category> list =categoryService.findAll();
	   	map.addAttribute("categories",list);
		map.addAttribute("category",new CategoryDto());
		return "admin/categories/categories";
	}
	
	@GetMapping("list/paginated")
	public String page(ModelMap map, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
	    int currentPage = page.orElse(0).intValue();
	    int pageSize = size.orElse(5).intValue();

	    Pageable pageable = PageRequest.of(currentPage, pageSize);
	    Page<Category> rePage = categoryService.findAll(pageable);

	    if (rePage != null) {
	        int totalPages = rePage.getTotalPages();
	        
	        if (totalPages > 0) {
	            int start = Math.max(1, currentPage - 2);
	            int end = Math.min(currentPage + 2, totalPages);

	            if (totalPages > 5) {
	                if (end == totalPages)
	                    start = end - 5;
	                else if (start == 1)
	                    end = start + 5;
	            }

	            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
	                    .boxed()
	                    .collect(Collectors.toList());
	            map.addAttribute("pageNumbers", pageNumbers);
	        }
	    } else {
	        // Xử lý khi rePage là null, ví dụ: hiển thị thông báo lỗi
	        map.addAttribute("errorMessage", "Không tìm thấy danh mục.");
	    }

	    map.addAttribute("categoryPage", rePage);
	    rePage.forEach(category -> System.out.println("List: " + category.toString()));

	    return "admin/categories/listCategory";
	}



}
