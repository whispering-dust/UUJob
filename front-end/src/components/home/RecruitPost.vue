<template>
    <div class="recruit-card">
        <el-form :model="form" label-width="80px">
            <el-form-item label="发布用户">
                <span>{{ form.userName }}</span>
            </el-form-item>

            <el-col style="width:500px">
                <el-form-item label="标题">
                    <el-input v-model="form.title" />
                </el-form-item>
            </el-col>

            <el-col style="width:500px">
                <el-form-item label="招聘岗位">
                    <el-input v-model="form.position" />
                </el-form-item>
            </el-col>

            <el-col style="width:500px">
                <el-form-item label="招聘工资">
                    <el-input v-model="form.salary" />
                </el-form-item>
            </el-col>
            <el-col style="width:300px">
                <el-form-item label="位置/城市">
                    <el-input v-model="form.location" />
                </el-form-item>
            </el-col>

            <el-form-item label="详细描述">
                <el-input v-model="form.description" type="textarea" style="min-height: 200px;width: 600px;" />
            </el-form-item>

            <el-form-item>
                <el-button type="primary" :icon="Check" @click="submit()">确认发布</el-button>
                <el-button type="danger" :icon="Delete" @click="dialogVisible = true">
                    取消发布
                </el-button>
            </el-form-item>
        </el-form>


        <el-dialog v-model="dialogVisible" title="Tips" width="30%" :before-close=true>
            <span>
                是否确定取消发布？
            </span>
            <template #footer>
                <span class="dialog-footer">
                    <el-button type="primary" @click="dialogVisible = false">不，点错了!</el-button>
                    <el-button type="warning" @click="dialogVisible = false; handlecancel()">是，就要撤！</el-button>
                </span>
            </template>
        </el-dialog>

    </div>
</template>

<script>
import { computed, ref, reactive } from 'vue'
import { ElMessageBox } from 'element-plus'
import { ElNotification } from 'element-plus'
import { Delete, Check } from '@element-plus/icons-vue'
import { useStore } from "vuex"
import axios from 'axios'


export default {
    data() {
        const dialogVisible = ref(false);
        const size = ref('');

        return {
            Delete, Check,
            dialogVisible,
            size,
            userId: useStore().state.userId,
            form: {
                userName: useStore().state.userName,
                title: "标题",
                position: "",
                salary: "",
                description: "",
                location: "",
            },
        }
    },
    methods: {
        handlecancel() {
            ElNotification({
                title: '已取消',
                message: '已取消！',
                type: 'success',
            })
        },
        async submit() {
            let that = this;
            //alert(this.userId)
            axios({
                method: "post",
                url: "http://localhost:9090/jobs",
                data: {
                    publisherId: that.userId,
                    title: that.form.title,
                    position: that.form.position,
                    description: that.form.description,
                    salary: that.form.salary,
                    location: that.form.location,
                },
            }).then(function (response) {
                alert(response.data.msg);
                if (response.data.code === 200) {
                    alert("操作成功");
                }
                else {
                    alert(response.data.msg);
                }
            });

        },

    },
    mounted() {
        alert(this.userId)
    },
}

</script>

<style scoped>
.recruit-card {
    height: 100%;
}

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

.dialog-footer button:first-child {
    margin-right: 10px;
}
</style>