/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
import http from './http'
export const api={
 login:data=>http.post('/auth/login',data),me:()=>http.get('/auth/me'),dashboard:()=>http.get('/dashboard'),
 customers:keyword=>http.get('/customers',{params:{keyword}}),customer:id=>http.get(`/customers/${id}`),createCustomer:data=>http.post('/customers',data),updateCustomer:(id,data)=>http.put(`/customers/${id}`,data),
 contacts:customerId=>http.get('/contacts',{params:{customerId}}),createContact:data=>http.post('/contacts',data),deleteContact:id=>http.delete(`/contacts/${id}`),
 opportunities:customerId=>http.get('/opportunities',{params:customerId?{customerId}:{}}),createOpportunity:data=>http.post('/opportunities',data),updateOpportunityStage:(id,data)=>http.patch(`/opportunities/${id}/stage`,data),
 followUps:customerId=>http.get('/follow-ups',{params:{customerId}}),recentFollowUps:()=>http.get('/follow-ups/recent'),createFollowUp:data=>http.post('/follow-ups',data),
 tasks:()=>http.get('/tasks'),createTask:data=>http.post('/tasks',data),setTask:(id,completed)=>http.patch(`/tasks/${id}`,{completed}),deleteTask:id=>http.delete(`/tasks/${id}`),
 opportunityForecast:data=>http.post('/customer-intelligence/opportunity-forecast',data)
}
