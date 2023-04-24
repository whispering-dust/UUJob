<template>
    <el-dialog :visible.sync="dialogVisible" title="编辑个人资料">
      <el-form :model="updatedProfile" label-width="120px">
        <el-form-item label="头像">
          <el-upload
            :action="uploadUrl"
            accept="image/*"
            :show-file-list="false"
            :on-success="handleUploadSuccess"
            :before-upload="beforeUpload"
            :headers="headers"
          >
            <el-button size="small" type="primary">点击上传</el-button>
            <div v-if="imageUrl" style="display:inline-block; margin-left: 10px">
              <el-image :src="imageUrl" style="width: 60px; height: 60px;"></el-image>
            </div>
          </el-upload>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="updatedProfile.name" style="width: 30%;"/>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="updatedProfile.sex">
            <el-radio label="男">男</el-radio>
            <el-radio label="女">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="联系电话"  style="width: 50%;">
          <el-input v-model="updatedProfile.phone" />
        </el-form-item>
        <el-form-item label="电子邮件"  style="width: 50%;">
          <el-input v-model="updatedProfile.email" />
        </el-form-item>
        <el-form-item label="学历"  style="width: 40%;">
          <el-select v-model="updatedProfile.edu" placeholder="请选择学历">
            <el-option label="大专" value="大专"></el-option>
            <el-option label="本科" value="本科"></el-option>
            <el-option label="硕士" value="硕士"></el-option>
            <el-option label="博士" value="博士"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所在单位"  style="width: 80%;">
          <el-input v-model="updatedProfile.college" />
        </el-form-item>
        <el-form-item label="专业"  style="width: 40%;">
          <el-input v-model="updatedProfile.major" />
        </el-form-item>
        <el-form-item label="意向地点"  style="width: 50%;">
          <el-select v-model="updatedProfile.expected_location" placeholder="请选择意向地点">
            <el-option label="北京" value="北京"></el-option>
            <el-option label="上海" value="上海"></el-option>
            <el-option label="广州" value="广州"></el-option>
            <el-option label="深圳" value="深圳"></el-option>
            <!-- 添加更多城市选项 -->
          </el-select>
        </el-form-item>
        <el-form-item label="自我介绍">
          <el-input type="textarea" :autosize="{ minRows: 4}" style="min-height:100px" v-model="updatedProfile.description" />
        </el-form-item>
        <el-form-item label="荣誉奖项">
          <el-input type="textarea" :autosize="{ minRows: 4}" style="min-height:100px" v-model="updatedProfile.honor" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="margin-left: 40%;">
        <el-button @click="closeDialog">取消</el-button>
        <el-button type="primary" @click="submitProfile">保存</el-button>
      </div>      
    </el-dialog>
  </template>
  
  <script>
  import axios from "axios";
  import { useStore } from "vuex";
  
  export default {
    props: {
      visible: {
        type: Boolean,
        default: false,
      },
      profile: {
        type: Object,
        default: () => ({}),
      },
    },
    data() {
      return {
        imageUrl: "",
        uploadUrl: "http://localhost:9090/users/upload-avatar",
        headers: {},

        dialogVisible: this.visible,
        updatedProfile: { ...this.profile },
      };
    },
    watch: {
      visible: function (newVal) {
        this.dialogVisible = newVal;
      },
      dialogVisible: function (newVal) {
        this.$emit("update:modelValue", newVal);
      },
      profile: {
        handler: function (newVal) {
          this.updatedProfile = { ...newVal };
          this.imageUrl = newVal.avatarUrl;
        },
        deep: true,
      },
    },
    methods: {
      async submitProfile() {
        try {
            const response = await axios.put("http://localhost:9090/users/profiles", {
                ...this.updatedProfile,
                userId: useStore().state.userId,
            });

            if (response.data.code === 200) {
                this.$emit("update", this.updatedProfile);
                this.$message.success("个人资料已更新");
                this.dialogVisible = false;
            } else {
                this.$message.error(response.data.msg);
            }
        } catch (error) {
            console.error(error);
            this.$message.error("更新个人资料失败");
        }
      },
      closeDialog() {
        this.$emit("close");
        this.dialogVisible = false;
      },
      beforeUpload(file) {
        const isImage = file.type.startsWith("image/");
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isImage) {
          this.$message.error("上传头像只能是图片格式!");
        }
        if (!isLt2M) {
          this.$message.error("上传头像大小不能超过 2MB!");
        }
        return isImage && isLt2M;
      },
      handleUploadSuccess(response, file) {
        if (response.code === 200) {
          this.imageUrl = URL.createObjectURL(file.raw);
          this.$message.success("头像上传成功");
          this.updatedProfile.avatarUrl = response.data.avatarUrl;
        } else {
          this.$message.error("头像上传失败");
        }
      },
    },
};
</script>

  