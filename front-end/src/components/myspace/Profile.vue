<template>
    <el-card class="mt-5 ml-5" style="height: 80%;width:80%;">
        <div class="row container">
            <div class="col-6 font-weight-bold" style="font-size: large;"> 我的简历</div>
            <div class="col-6 float-right" style="margin-right:0px;text-align: right;">
                <el-button type="success" style="background-color: black;" @click="edit"><el-icon>
                        <Edit />
                    </el-icon>编辑</el-button>
            </div>
        </div>
        <div class="row">
            <div class="col-2">
                <div class="block mt-3">
                    <el-avatar shape="square" style="width:100%;min-height:150px;" :src="userAvator" />
                </div>
            </div>
            <div class="col-10">
                <el-descriptions title="" :column="2" border>
                    <el-descriptions-item>
                        <template #label>
                            <div class="cell-item">
                                <el-icon>
                                    <user />
                                </el-icon>
                                姓名
                            </div>
                        </template>
                        {{ Profile.name }}
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
                        {{ Profile.sex }}
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
                        {{ Profile.phone }}
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
                        {{ Profile.email }}
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
                        {{ Profile.edu }}
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
                        {{ Profile.college }}
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
                        {{ Profile.major }}
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
                        {{ Profile.expected_location }}
                    </el-descriptions-item>

                </el-descriptions>
            </div>
        </div>


        <el-descriptions class="margin-top" direction="vertical" :column="1" border>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        自我介绍
                    </div>
                </template>
                <div style="min-height:100px">
                    {{ Profile.description }}
                </div>
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        荣誉奖项
                    </div>
                </template>
                <div style="min-height:100px">
                    {{ Profile.honor }}
                </div>
            </el-descriptions-item>
        </el-descriptions>
    </el-card>

    <profile-edit v-model="dialogVisible" :profile="Profile" @update="updateProfile" @close="closeDialog"></profile-edit>
</template>
  
<script>
import { computed, ref, watch } from 'vue'
import { Edit } from '@element-plus/icons-vue'
import {
    Iphone,
    Location,
    OfficeBuilding,
    Tickets,
    User,
} from '@element-plus/icons-vue';
import { useStore } from "vuex";
import axios from "axios";
import ProfileEdit from '@/components/myspace/ProfileEdit.vue'

export default {
    components: {
        ProfileEdit
    },
    data() {
        return {
            userAvator: '',
            dialogVisible: false,
            Profile: {
                name: "张三",
                sex: "男",
                phone: "11451478910",
                email: "11@qq.com",
                edu: "学历",
                college: "所在单位",
                major: "专业",
                expected_location: "意向城市",
                description: "描述",
                honor: "荣誉",
                admissionDate: '',
                graduationDate: '',
            },
            store: useStore(),

        }
    },
    methods: {
        edit() {
            this.dialogVisible = true;
        },
        updateProfile(updatedProfile) {
            this.Profile = updatedProfile;
            this.dialogVisible = false;
        },
        closeDialog() {
            this.dialogVisible = false;
        },
        async getProfile() {
            let that = this;
            //首先从user表里面获取部分信息
            axios({
                method: "get",
                url: "http://localhost:9090/users/profiles",
                params: {
                    userId: useStore().state.userId
                }

            }).then(function (response) {
                if (response.data.code === 200) {
                    that.Profile = [];
                    that.Profile.name = response.data.data.name;
                    that.Profile.phone = response.data.data.phone;
                    that.Profile.sex = response.data.data.sex;
                    that.Profile.email = response.data.data.email;
                    that.Profile.edu = response.data.data.education;
                    that.Profile.college = response.data.data.college;
                    that.Profile.major = response.data.data.major;
                    that.Profile.expected_location = response.data.data.expectedLocation;
                    that.Profile.description = response.data.data.personalDescription;
                    that.Profile.honor = response.data.data.reward;
                    that.Profile.admissionDate = response.data.data.admissionDate
                    that.Profile.graduationDate = response.data.data.graduationDate

                    that.store.commit("setProfile", response.data.data.id);
                    alert('profileId:  ' + that.store.state.profileId)

                    //alert(that.Profile);
                }
                else {
                    alert(response.data.msg);

                }
            });



        },

    },
    mounted() {
        this.userAvator = useStore().state.userAvator
        alert(useStore().state.userId);
        this.getProfile();
    },
    watch: {
        // dialogVisible(newValue) {
        // if (!newValue) {
        //   this.getProfile();
        // }
        //},
    }
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
  