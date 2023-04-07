<template>  
    <el-card class="mt-5 ml-5" style="height: 100%;width:80%;">
        <div class="row container">
            <div class="col-6 font-weight-bold" style="font-size: large;"> 我的简历</div>
            <div  class="col-6 float-right" style="margin-right:0px;text-align: right;">
                <el-button type="primary" @click="init" ><el-icon><Edit /></el-icon></el-button>
            </div>
        </div>
        <div class="row">
            <div class="col-2">
                <div class="block mt-3">
                    <el-avatar shape="square" style="width:100%;min-height:150px;" :src="squareUrl" />
                </div>
            </div>
            <div class="col-10">
                <el-descriptions
                title=""
                :column="2"
                border
                >
                <el-descriptions-item> 
                    <template #label>
                    <div class="cell-item">
                        <el-icon>
                        <user />
                        </el-icon>
                        姓名
                    </div>
                    </template>
                    {{Profile.name}}
                </el-descriptions-item>
                <el-descriptions-item>
                    <template #label>
                    <div class="cell-item">
                        <el-icon>
                        <user />
                        </el-icon>
                        性别
                    </div>
                    </template>
                    {{Profile.sex}}
                </el-descriptions-item>
                <el-descriptions-item>
                    <template #label>
                    <div class="cell-item">
                        <el-icon>
                        <iphone />
                        </el-icon>
                        联系电话
                    </div>
                    </template>
                    {{Profile.phone}}
                </el-descriptions-item>
                <el-descriptions-item>
                    <template #label>
                    <div class="cell-item">
                        <el-icon>
                        <location />
                        </el-icon>
                        电子邮件
                    </div>
                    </template>
                    {{Profile.email}}
                </el-descriptions-item>
                <el-descriptions-item>
                    <template #label>
                    <div class="cell-item">
                        <el-icon>
                        <tickets />
                        </el-icon>
                        学历
                    </div>
                    </template>
                    {{Profile.edu}}
                </el-descriptions-item>
                <el-descriptions-item>
                    <template #label>
                    <div class="cell-item">
                        <el-icon>
                        <office-building />
                        </el-icon>
                        所在单位
                    </div>
                    </template>
                    {{Profile.college}}
                </el-descriptions-item>
                <el-descriptions-item>
                    <template #label>
                    <div class="cell-item">
                        <el-icon>
                        <office-building />
                        </el-icon>
                        专业
                    </div>
                    </template>
                    {{Profile.major}}
                </el-descriptions-item>
                <el-descriptions-item>
                    <template #label>
                    <div class="cell-item">
                        <el-icon>
                        <office-building />
                        </el-icon>
                        意向地点
                    </div>
                    </template>
                    {{Profile.expected_location}}
                </el-descriptions-item>
                
                </el-descriptions>
            </div>
        </div>
        

        <el-descriptions
            class="margin-top"
            direction="vertical"
            :column="1"
            border>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        自我介绍
                    </div>
                </template>               
                {{Profile.description}}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        荣誉奖项
                    </div>
                </template>               
                {{Profile.honor}}
            </el-descriptions-item>
        </el-descriptions>
    </el-card>
    
  
   
  </template>
  
  <script>
  import { computed, ref } from 'vue'
  import { Edit } from '@element-plus/icons-vue'
  import {
    Iphone,
    Location,
    OfficeBuilding,
    Tickets,
    User,
  } from '@element-plus/icons-vue';
  import {useStore} from "vuex";
  import axios from "axios";
  
  export default{
    data(){
        return {
            Profile:{
                name:"张三",
                sex:"男",
                phone:"11451478910",
                email:"11@qq.com",
                edu:"学历",
                college:"所在单位",
                major:"专业",
                expected_location:"意向城市",
                description:"描述",
                honor:"荣誉",
            }

        }
    },
    methods: {
        async getProfile(){
            let that=this; 
            //首先从user表里面获取部分信息
            axios({
                method: "get",
                url: "http://localhost:9090/user/info",
                params: {
                    id: useStore().state.userId
                }
                // data: {
                //     //参数自己接
                //     id: that.useStore().state.userId,
                // },
            }).then(function (response) {
                if(response.data.code === "200"){
                    that.Profile = [];
                    that.Profile.name = response.data.data.name;
                    that.Profile.phone = response.data.data.phone;
                    that.Profile.company = response.data.data.company;
                    that.Profile.position = response.data.data.position;
                }
                else{
                    alert(response.data.msg);
                }
            });

            //然后从profile表里面获取部分信息
            axios({
                method: "get",
                url: "http://localhost:9090/profile",
                params: {
                    id: useStore().state.userId
                }
            }).then(function (response) {
                if(response.data.code==="200"){
                    
                    that.Profile.sex = response.data.data.sex;
                    that.Profile.edu = response.data.data.edu;
                    that.Profile.email = response.data.data.email;
                    that.Profile.description = response.data.data.description;
                }
                else{
                    alert(response.data.msg);
                }
            });

        },

    },
    mounted() {
        this.getProfile();
    },
  }
  
  </script>
  
  <style scoped>
  .el-descriptions {
    margin-top: 20px;
  }
  .cell-item {
    display: flex;
    align-items: center;
  }
  .margin-top {
    margin-top: 20px;
  }
  </style>
  