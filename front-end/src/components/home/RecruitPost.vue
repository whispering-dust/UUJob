<template>
    <div class="recruit-card">
        <el-form :model="form" label-width="80px">
            <el-form-item label="发布用户">
                <span>{{ form.userName }}</span>
            </el-form-item>

            <div style="width:500px">
                <el-form-item label="标题">
                    <el-input v-model="form.title" />
                </el-form-item>
            </div>

            <div style="width:500px">
                <el-form-item label="招聘岗位">
                    <!-- <el-input v-model="form.position" /> -->
                    <el-select v-model="form.position" filterable placeholder="Select" @change="handlePositionChange">
                        <el-option v-for="item in positions" :key="item.id" :label="item.value" :value="item.id" />
                    </el-select>
                    <div style="font-size: 10px;">（请在框内输入要招聘岗位关键词以搜索类别词条）</div>
                </el-form-item>
            </div>

            <div style="width:500px;display:flex;align-content:center">
                <el-form-item label="招聘工资">
                    <el-input-number v-model="form.salary" :min="0" :max="1000000" />
                </el-form-item>
                <p class="ml-4 mt-2">元/每月</p>
            </div>
            <div style="width:300px">
                <el-form-item label="位置/城市">
                    <el-select v-model="form.location" placeholder="请选择城市">
                        <el-option v-for="city in cities" :key="city.value" :label="city.label" :value="city.value" />
                    </el-select>
                </el-form-item>
            </div>

            <div style="width: 300px">
                <el-form-item label="教育信息">
                    <el-select v-model="form.education" placeholder="请选择教育信息">
                        <el-option label="博士" value="博士" />
                        <el-option label="硕士" value="硕士" />
                        <el-option label="本科" value="本科" />
                        <el-option label="大专" value="大专" />
                        <el-option label="中专" value="中专" />
                        <el-option label="无要求" value="无要求" />
                    </el-select>
                </el-form-item>
            </div>

            <el-form-item label="详细描述">
                <el-input v-model="form.description" type="textarea" :autosize="{ minRows: 5 }" style="width: 600px;" />
            </el-form-item>

            <el-form-item label="简历模板">
                <el-upload
                    ref="upload"
                    class="upload-demo"
                    :limit="1"
                    :on-exceed="handleExceed"
                    :auto-upload="false"
                    :on-change="handleChange"
                >
                    <template #trigger>
                        <el-button ><el-icon><Upload /></el-icon>上传本地文件</el-button>
                    </template>
                    <template #tip>
                    <div class="el-upload__tip text-red">
                        一次只能上传一个文件
                    </div>
                    </template>
                </el-upload>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" icon="Check" @click="submit()">确认发布</el-button>
                <el-button type="danger" icon="Delete" @click="dialogVisible = true">
                    取消发布
                </el-button>
            </el-form-item>
        </el-form>


        <el-dialog v-model="dialogVisible" title="Tips">
            <span>
                是否确定取消发布？
            </span>
            <template #footer>
                <span class="dialog-footer">
                    <el-button type="info" @click="dialogVisible = false">不，点错了!</el-button>
                    <el-button type="danger" @click="dialogVisible = false; handlecancel()">是，就要撤！</el-button>
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
import axios from 'axios';
import { ElMessage } from 'element-plus'

export default {
    data() {
        const dialogVisible = ref(false);
        const size = ref('');
        const successMsg = (value) => {
            ElMessage({
                message: value,
                type: 'success',
            })
        }
        const warningMsg = (value) => {
            ElMessage({
                message: value,
                type: 'warning',
            })
        }
        const errorMsg = (value) => {
            ElMessage.error(value)
        }
        return {
            Delete, Check,
            dialogVisible,
            size,
            cities: [
                { value: '北京', label: '北京' },
                { value: '上海', label: '上海' },
                { value: '广州', label: '广州' },
                { value: '深圳', label: '深圳' },
                { value: '杭州', label: '杭州' },
                { value: '武汉', label: '武汉' },
                { value: '天津', label: '天津' },
                { value: '苏州', label: '苏州' },
                { value: '南京', label: '南京' },
                // 其他城市
            ],
            positions: [],
            userId: useStore().state.userId,
            userName: useStore().state.userName,
            form: {
                userName: useStore().state.userName,
                title: null,
                positionId: null,
                education: null,
                position: null,
                salary: null,
                description: null,
                location: null,
            },
            successMsg,
            warningMsg,
            errorMsg,
            fileToUpload: null,
        }
        
    },
    methods: {
        handlePositionChange(value) {
            const selectedPosition = this.positions.find(item => item.id === value);
            this.form.position = selectedPosition.value;
            this.form.positionId = selectedPosition.id;
        },
        handlecancel() {
            this.form = {
                userName: this.userName,
                title: null,
                positionId: null,
                education: null,
                position: null,
                salary: null,
                description: null,
                location: null,
            }
            ElNotification({
                title: '已取消',
                message: '已取消！',
                type: 'success',
            });
            this.$emit('cancel');
        },
        async submit() {
            let that = this;
            //console.log("提交前", this.form)
            //alert(this.userId)
            console.log(this.form)
            if (this.checkout()) {
                axios({
                    method: "post",
                    url: "http://localhost:9090/jobs",
                    data: {
                        publisherId: that.userId,
                        title: that.form.title,
                        positionId: that.form.positionId,
                        position: that.form.position,
                        education: that.form.education,
                        description: that.form.description,
                        salary: that.form.salary + "元/月",
                        location: that.form.location,
                    },
                }).then(function (response) {
                    if (response.data.code === 200) {
                        that.uploadFile(response.data.data);
                        that.$message.success("提交操作成功");
                        that.form = {
                            userName: that.userName,
                            title: null,
                            positionId: null,
                            education: null,
                            position: null,
                            salary: null,
                            description: null,
                            location: null,
                        }
                        that.$emit('cancel');
                    }
                    else {
                        that.$message.error(response.data.msg);
                        that.errorMsg(response.data.msg)
                    }


                });
            }


        },
        async getPositions() {
            const response = await axios.get('http://localhost:9090/jobs/positions');
            if (response.data.code == 200) {
                console.log(response.data.data);
                this.positions = response.data.data.map(item => ({
                    id: item.id, value: item.positionName
                }));
            } else {

                this.$message.error(response.data.msg);
                that.errorMsg(response.data.msg)
            }
        },
        checkout() {
            for (var i in this.form) {
                console.log()
                if (this.form[i] == null) {
                    this.errorMsg("表单有内容为空！请填写完成再提交")
                    return false;
                }
            }
            if (this.form.description == "") {
                this.errorMsg("描述为空，请填写后再提交")
                return false;
            }
            if (this.form.title == "") {
                this.errorMsg("标题为空，请填写后再提交")
                return false;
            }
            return true;
        },
        handleChange(file, fileList) {
            this.fileToUpload = file;
        },
        uploadFile(id) {
            console.log(id);
            let formData = new FormData();
            
            formData.append("jobId", id);
            formData.append("template", this.fileToUpload.raw);

            axios.post("http://localhost:9090/jobs/upload-files", formData)
            .then(response => {
                if (response.data.code === 200) {
                    this.successMsg("文件上传成功");
                } else {
                    this.errorMsg(response.data.msg);
                }
            }).catch(error => {
                this.errorMsg("文件上传失败");
            });
        },


    },
    mounted() {
        //alert(this.userId)
        this.getPositions()

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