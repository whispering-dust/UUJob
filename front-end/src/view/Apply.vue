<template>
    <div style="min-height: 1080px;background-color: rgb(223, 229, 235);">
        <div class="bg-tertiary text-white px-4 px-lg-5 py-5 rounded-0 border-0 mb-0" style="height: 300px;">
            <div class="container">
                <el-card class="mt-5 ml-5" style="height: 100%;width:80%;">
                <div class="row container" style="display: flex;justify-content: center;">
                    <div class="font-weight-bold" style="font-size: large;margin-left:45% ; margin-right: auto;">投递简历</div>
                    <el-button  type="success" style="background-color: black;" @click="download"><el-icon><Download /></el-icon>下载模板</el-button>
                </div>
                <div class="row mt-3">
                    <div class="col-4" style="padding-left: 100px;">
                        <el-upload
                            ref="upload"
                            class="upload-demo"
                            :limit="1"
                            :on-exceed="handleExceed"
                            :auto-upload="false"
                            :on-change="handleChange"
                        >
                        <template #trigger>
                            <el-button type="success" style="background-color: black;"><el-icon><Upload /></el-icon>上传简历 (PDF)</el-button>
                        </template>
                        <template #tip>
                            <div class="el-upload__tip text-red">
                                一次只能上传一个文件
                            </div>
                            </template>
                            
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
import {useRoute,useRouter} from 'vue-router'
import { useStore } from "vuex";
import axios from "axios";

export default {
    components: {
        Profile,
    },
    data() {
        return {
            router:useRouter(),
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
            profileId: useStore().state.profileId,
            templateUrl: null,
            fileToUpload:null,
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
            let that = this;
            console.log("in Apply.vue: ",that.profileId,that.jobId);
            axios({
                method: 'post',
                url: 'http://localhost:9090/jobs/applications',
                data:{
                    profileId: that.profileId,
                    jobId: that.jobId,
                }
            })
            .then((response) => {
            if (response.data.code === 200) {
                console.log(response.data);
                this.uploadFile(response.data.data);
                this.$message.success('简历投递成功！');
                this.router.push("/home")
            } else {
                this.$message.error('简历投递失败，改简历已投过！');
            }
            })
            .catch((error) => {
                console.error(error);
                this.$message.error('简历投递失败，请重试！');
            });
        },
        async download(){
            //根据jobId获取templateUrl
            try {
                const response = await axios.get("http://localhost:9090/jobs", {
                    params: {
                        id: this.jobId,
                    },
                });

                if (response.data.code === 200) {
                    this.templateUrl=response.data.data.templateUrl
                    const a = document.createElement('a')
                    a.href = this.templateUrl
                    a.download = "简历模板.pdf" // 下载后文件名
                    //a.setAttribute('download', '简历模板.pdf') // 指定下载后的文件名，防跳转
                    a.style.display = 'none'
                    document.body.appendChild(a)
                    a.click() // 点击下载
                    document.body.removeChild(a) // 下载完成移除元素

                    // 下载pdf文件
                    // const responsePdf = await axios.get(this.templateUrl, {
                    //     responseType: 'blob', // this is important to get it as a Blob
                    // });

                    // if (responsePdf.status === 200) {
                    //     const blob = new Blob([responsePdf.data], 
                    //     {  type: 'application/octet-stream',
                    //         'Content-Disposition':'attachment' }); // create blob from response
                    //     const link = document.createElement('a'); // create a link element
                    //     link.href = window.URL.createObjectURL(blob); // create a URL for the blob
                    //     link.download = 'download.pdf'; // provide the name of the file to download
                    //     link.click(); // simulate a click to start the download

                    //     // clean up
                    //     URL.revokeObjectURL(link.href); // revoke the URL
                    //     document.body.removeChild(link); // remove the link from the body
                    // } else {
                    //     alert('Failed to download file');
                    // }

                } else {
                    alert(response.data.msg);
                }
            } catch (error) {
                console.error("Failed to fetch job:", error);
            }
        },
        handleChange(file, fileList) {
            this.fileToUpload = file;
        },
        uploadFile(id) {
            console.log(id);
            let formData = new FormData();
            
            formData.append("applicationId", id);
            formData.append("annex", this.fileToUpload.raw);

            axios.post("http://localhost:9090/jobs/applications/upload-files", formData)
            .then(response => {
                if (response.data.code === 200) {
                    this.$message.success("文件上传成功");
                } else {
                    this.$message.error(response.data.msg);
                }
            }).catch(error => {
                this.errorMsg("文件上传失败");
            });
        },
    },

}

</script>

<style scoped>

</style>