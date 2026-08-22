<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ -->
<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { showSuccessToast } from 'vant'
import { api } from '../api/crm'

const items = ref([]), filter = ref('ACTIVE'), selected = ref(null), forecast = ref(null)
const form = reactive({ stage: 'LEAD', probability: 10, nextStep: '' })
const stages = { LEAD:'初步接触', DISCOVERY:'需求确认', PROPOSAL:'方案报价', NEGOTIATION:'商务谈判', WON:'赢单', LOST:'输单' }
const stageOrder = ['LEAD','DISCOVERY','PROPOSAL','NEGOTIATION','WON','LOST']
const filtered = computed(() => items.value.filter(x => filter.value === 'ALL'
  || (filter.value === 'ACTIVE' && !['WON','LOST'].includes(x.stage)) || x.stage === filter.value))
const total = computed(() => filtered.value.reduce((sum, item) => sum + Number(item.amount), 0))

async function load() {
  items.value = await api.opportunities()
  const active = items.value.filter(item => !['WON','LOST'].includes(item.stage))
  if (active.length) forecast.value = await api.opportunityForecast({
    quarterTarget: 500000,
    deals: active.map((item, index) => ({
      name: item.name, amount: item.amount, probability: item.probability, stage: item.stage,
      expectedCloseDate: item.expectedCloseDate, daysSinceActivity: item.nextStep ? index + 2 : 18,
      criticalBlocker: item.stage === 'NEGOTIATION' && item.probability < 50
    }))
  })
}
function edit(item) { selected.value=item; form.stage=item.stage; form.probability=item.probability; form.nextStep=item.nextStep||'' }
async function save() { await api.updateOpportunityStage(selected.value.id,{...form,probability:Number(form.probability)}); selected.value=null; showSuccessToast('商机阶段已更新'); load() }
onMounted(load)
</script>

<template>
  <div class="page safe-top">
    <h1 class="page-title">销售商机</h1>
    <section class="pipeline-card brand-gradient"><div><span>当前商机金额</span><b>¥ {{ total.toLocaleString() }}</b></div><van-icon name="chart-trending-o" size="34"/></section>
    <section v-if="forecast" class="card forecast-card">
      <div><span>加权预测</span><b>¥ {{ Number(forecast.weightedForecast).toLocaleString() }}</b></div>
      <div><span>目标覆盖</span><b>{{ Math.round(forecast.targetCoverage*100) }}%</b></div>
      <div><span>风险商机</span><b>{{ forecast.atRiskDeals }}</b></div>
      <p>{{ forecast.guidance }}</p>
    </section>
    <div class="filters"><button v-for="x in [['ACTIVE','进行中'],['WON','赢单'],['LOST','输单'],['ALL','全部']]" :key="x[0]" :class="{active:filter===x[0]}" @click="filter=x[0]">{{ x[1] }}</button></div>
    <section v-for="item in filtered" :key="item.id" class="card opp" @click="edit(item)">
      <div class="opp-head"><div><div class="list-title">{{ item.name }}</div><div class="muted">{{ item.customerName }}</div></div><span class="tag">{{ stages[item.stage] }}</span></div>
      <div class="money">¥ {{ Number(item.amount).toLocaleString() }}</div><div class="progress"><i :style="{width:`${item.probability}%`}"></i></div>
      <div class="meta"><span>成交概率 {{ item.probability }}%</span><span>{{ item.expectedCloseDate||'未定日期' }}</span></div><p v-if="item.nextStep">下一步：{{ item.nextStep }}</p>
    </section>
    <div v-if="!filtered.length" class="empty">暂无该阶段商机</div>
    <van-popup v-model:show="selected" position="bottom" round><div v-if="selected" class="form"><h2>推进商机</h2><div class="customer-name">{{ selected.customerName }} · {{ selected.name }}</div><div class="stage-list"><button v-for="x in stageOrder" :key="x" :class="{active:form.stage===x}" @click="form.stage=x">{{ stages[x] }}</button></div><van-field v-model="form.probability" label="成交概率" type="number" suffix="%"/><van-field v-model="form.nextStep" label="下一步计划" type="textarea" rows="3"/><button class="action-btn" @click="save">保存商机阶段</button></div></van-popup>
  </div>
</template>

<style scoped>
.pipeline-card{border-radius:22px;padding:22px;display:flex;align-items:center;justify-content:space-between}.pipeline-card span{display:block;font-size:12px;opacity:.78}.pipeline-card b{display:block;font-size:25px;margin-top:8px}.forecast-card{display:grid;grid-template-columns:repeat(3,1fr);gap:10px;margin-top:12px}.forecast-card div{padding:10px;border-radius:10px;background:#f1f5fa}.forecast-card span{display:block;color:#7d8998;font-size:10px}.forecast-card b{display:block;margin-top:5px;color:#2856a8;font-size:16px}.forecast-card p{grid-column:1/-1;margin:0;color:#68768b;font-size:11px}.filters{display:flex;gap:8px;overflow:auto;margin:16px 0}.filters button,.stage-list button{border:0;background:#e9edf3;color:#68768b;border-radius:10px;padding:8px 13px;white-space:nowrap}.filters button.active,.stage-list button.active{background:#2856a8;color:#fff}.opp-head{display:flex;justify-content:space-between}.opp-head .muted{margin-top:5px}.money{font-size:22px;font-weight:750;color:#2856a8;margin:16px 0 9px}.progress{height:5px;background:#e8ecf2;border-radius:4px;overflow:hidden}.progress i{display:block;height:100%;background:#4a77c7}.meta{display:flex;justify-content:space-between;color:#8a95a6;font-size:11px;margin-top:7px}.opp p{margin:12px 0 0;font-size:12px;color:#596a81}.form{padding:18px 20px 32px}.form h2{margin:0}.customer-name{color:#7e8999;margin:8px 0 16px}.stage-list{display:flex;gap:8px;overflow:auto;padding-bottom:15px}.stage-list button{padding:8px 11px}.action-btn{margin-top:15px}
</style>
