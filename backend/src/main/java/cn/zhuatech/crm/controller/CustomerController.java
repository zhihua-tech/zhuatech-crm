/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.controller;

import cn.zhuatech.crm.common.ApiResponse;
import cn.zhuatech.crm.dto.CrmDto.*;
import cn.zhuatech.crm.model.Customer;
import cn.zhuatech.crm.repository.CustomerRepository;
import cn.zhuatech.crm.service.CrmAccessService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerRepository customers; private final CrmAccessService access;
    public CustomerController(CustomerRepository customers,CrmAccessService access){this.customers=customers;this.access=access;}
    @GetMapping public ApiResponse<List<CustomerView>> list(@RequestParam(defaultValue="") String keyword){
        var user=access.current(); var items=access.canViewAll(user)?customers.findAllByOrderByUpdatedAtDesc():customers.findByOwnerOrderByUpdatedAtDesc(user); String q=keyword.trim().toLowerCase(Locale.ROOT);
        return ApiResponse.ok(items.stream().filter(c->q.isEmpty()||(c.getName()+Objects.toString(c.getShortName(),"")+Objects.toString(c.getIndustry(),"")).toLowerCase(Locale.ROOT).contains(q)).map(CustomerView::from).toList());
    }
    @GetMapping("/{id}") public ApiResponse<CustomerView> detail(@PathVariable Long id){return ApiResponse.ok(CustomerView.from(access.customer(id)));}
    @PostMapping public ApiResponse<CustomerView> create(@Valid @RequestBody CustomerRequest req){Customer c=new Customer(req.name(),access.current());apply(c,req);return ApiResponse.ok("客户已创建",CustomerView.from(customers.save(c)));}
    @PutMapping("/{id}") public ApiResponse<CustomerView> update(@PathVariable Long id,@Valid @RequestBody CustomerRequest req){Customer c=access.customer(id);apply(c,req);return ApiResponse.ok("客户已更新",CustomerView.from(customers.save(c)));}
    private void apply(Customer c,CustomerRequest r){c.update(r.name(),r.shortName(),r.industry(),r.level()==null?"B":r.level(),r.status(),r.source(),r.phone(),r.email(),r.address(),r.nextFollowUpDate(),r.notes());}
}
