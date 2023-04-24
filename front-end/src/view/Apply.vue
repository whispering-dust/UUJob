<template>
    <div style="min-height: 1080px;background-color: rgb(223, 229, 235);">
        <div class="bg-tertiary text-white px-4 px-lg-5 py-5 rounded-0 border-0 mb-0" style="height: 300px;">
            <div class="container">
                <el-card class="mt-5 ml-5" style="height: 100%;width:80%;">
                <div class="row container">
                    <div class="col-6 font-weight-bold" style="font-size: large;margin-left:40%">投递简历</div>
                </div>
                <div class="row mt-3">
                    <div class="col-4" style="padding-left: 100px;">
                        <el-upload
                            action="http://localhost:9090/upload"
                            :before-upload="beforeUpload"
                            accept="application/pdf"
                            :on-success="handleUploadSuccess"
                            :on-error="handleUploadError"
                            :show-file-list="false"
                        >
                            <el-button type="success" style="background-color: black;" icon="el-icon-upload">导入简历 (PDF)</el-button>
                        </el-upload>
                    
                    </div>
                    <div class="col-8 pt-1"><p>已经有简历? 那就上传你的简历🤗🤗🤗🤗🤗🤗🤗🤗</p></div>
                </div>
                <div style="display: flex;
                    justify-content: center;
                    align-items: center;">
                    <profile :profile="profile"/>
                </div>
                <div class="row mt-5 mb-3">
                    <div style="margin-left: 40%;">
                    <el-button type="success" style="background-color: black; width:150px" @click="submitResume">投递简历</el-button>
                    </div>
                </div>
                </el-card>
            </div>
        </div>
    </div>
</template>

<script>
import Profile from '@/components/myspace/Profile.vue';
import {useRoute} from 'vue-router'

export default {
    components: {
        Profile,
    },
    data() {
        return {
            jobId: useRoute().params.jobId,
            profile: {
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
            },
        }
    },
    mounted(){
        
    },
    methods: {
        beforeUpload(file) {
            const isPdf = file.type === 'application/pdf';
            if (!isPdf) {
                this.$message.error('仅支持上传 PDF 格式的文件！');
            }
            return isPdf;
        },
        handleUploadSuccess(response, file) {
            this.$message.success('文件上传成功！');
            // 这里可以处理后端返回的文件地址等信息，例如：
            // this.profile.pdfUrl = response.data.url;
        },
        handleUploadError(error, file) {
            this.$message.error('文件上传失败，请重试！');
        },
        submitResume() {
            axios({
                method: 'post',
                url: 'http://localhost:9090/api/submit_resume',
                data: this.profile,
            })
            .then((response) => {
            if (response.data.code === 200) {
                this.$message.success('简历投递成功！');
            } else {
                this.$message.error('简历投递失败，请重试！');
            }
            })
            .catch((error) => {
            console.error(error);
            this.$message.error('简历投递失败，请重试！');
            });
        },
    },

}

</script>

<style scoped>

</style>