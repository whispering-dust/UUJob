<template>
    <el-container style="height: 100%;">
        <el-aside width="200px" >
            <h4 class="pt-2 mb-0 pl-3 pb-3" style="color:aliceblue; background-color: #343a40; overflow:hidden">
                管理员：{{userName}}</h4>
            <el-menu
                active-text-color="#a3cef1"
                background-color="#343a40"
                class="el-menu-vertical-demo"
                text-color="#fff"
                style="width: 100%; border-right: 0px;"                
            >
                <el-menu-item index="1" @click="handleSelect(1)">
                    <el-icon><View /></el-icon>
                    <span>信息概览</span>
                </el-menu-item>

                <el-menu-item index="2" @click="handleSelect(2)">
                    <el-icon><Crop /></el-icon>
                    <span>处理举报</span>
                </el-menu-item>

                <el-menu-item index="3" @click="handleSelect(3)">
                    <el-icon><setting /></el-icon>
                    <span>审核</span>
                    </el-menu-item>
            </el-menu>
        </el-aside>

        <el-main>
            <InfoOverview v-if="selectedComponent == 'InfoOverview'" />
            <HandleReport v-if="selectedComponent == 'HandleReport'" />
            <Audit v-if="selectedComponent == 'Audit'" />
        </el-main>
    </el-container>

</template>

<script>
import { useStore } from "vuex";
import InfoOverview from "@/components/management/InfoOverview.vue";
import HandleReport from "@/components/management/HandleReport.vue";
import Audit from "@/components/management/Audit.vue";

export default{
    components: {
        InfoOverview,
        HandleReport,
        Audit
    },
    data() {
        return {
            userName: useStore().state.userName,
            selectedComponent: 'InfoOverview'
        }
    },
    methods: {
        handleSelect(index) {
            switch (index) {
                case 1:
                    this.selectedComponent = 'InfoOverview';
                    break;
                case 2:
                    this.selectedComponent = 'HandleReport';
                    break;
                case 3:
                    this.selectedComponent = 'Audit';
                    break;
            }
        }
    }
}
</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 150px;
    min-height: 800px;
  }
</style>