/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
import { defineStore } from 'pinia'
import { api } from '../api/crm'
export const useAuthStore = defineStore('auth', { state:()=>({ user: JSON.parse(localStorage.getItem('zhuatech_crm_user') || 'null') }), actions:{ async login(form){ const result=await api.login(form); localStorage.setItem('zhuatech_crm_token',result.token); this.user=result.user; localStorage.setItem('zhuatech_crm_user',JSON.stringify(result.user)) }, logout(){ localStorage.removeItem('zhuatech_crm_token'); localStorage.removeItem('zhuatech_crm_user'); this.user=null } } })
