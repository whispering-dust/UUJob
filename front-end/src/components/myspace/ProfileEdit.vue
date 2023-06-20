<template>
  <el-dialog :visible.sync="dialogVisible" title="编辑个人资料">
    <el-form :model="updatedProfile" label-width="120px">

      <el-form-item label="简历头像">
        <el-upload ref="upload" class="upload-demo" :show-file-list="false" :on-change="handleAvatarChange"
          :auto-upload="false">
          <template #trigger>
            <el-button size="small" type="primary">点击上传</el-button>
          </template>
        </el-upload>
      </el-form-item>

      <el-form-item label="姓名">
        <el-input v-model="updatedProfile.name" style="width: 30%;" />
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group v-model="updatedProfile.sex">
          <el-radio label="男">男</el-radio>
          <el-radio label="女">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="联系电话" style="width: 50%;">
        <el-input v-model="updatedProfile.phone" />
      </el-form-item>
      <el-form-item label="电子邮件" style="width: 50%;">
        <el-input v-model="updatedProfile.email" />
      </el-form-item>
      <el-form-item label="学历" style="width: 40%;">
        <el-select v-model="updatedProfile.edu" placeholder="请选择学历">
          <el-option label="大专" value="大专"></el-option>
          <el-option label="本科" value="本科"></el-option>
          <el-option label="硕士" value="硕士"></el-option>
          <el-option label="博士" value="博士"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="毕业院校" style="width: 80%;">
        <el-input v-model="updatedProfile.college" />
      </el-form-item>
      <el-form-item label="专业" style="width: 40%;">
        <el-input v-model="updatedProfile.major" />
      </el-form-item>
      <el-form-item label="意向地点" style="width: 50%;">
        <el-select v-model="updatedProfile.expected_location" placeholder="请选择意向地点">
          <el-option label="北京" value="北京"></el-option>
          <el-option label="上海" value="上海"></el-option>
          <el-option label="广州" value="广州"></el-option>
          <el-option label="深圳" value="深圳"></el-option>
          <!-- 添加更多城市选项 -->
        </el-select>
      </el-form-item>
      <el-form-item label="自我介绍">
        <el-input type="textarea" :autosize="{ minRows: 4 }" style="min-height:100px"
          v-model="updatedProfile.description" />
      </el-form-item>
      <el-form-item label="荣誉奖项">
        <el-input type="textarea" :autosize="{ minRows: 4 }" style="min-height:100px" v-model="updatedProfile.honor" />
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

      dialogVisible: this.visible,
      updatedProfile: { ...this.profile },
      seekerId: useStore().state.userId,
      profileId: null,
      store: useStore(),
      avatarToUpload: null,
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
  mounted() {
    console.log('初始加载profileId', this.store.state.profileId)
    this.profileId = this.store.state.profileId;
  },
  methods: {
    async submitProfile() {
      console.log("传给后端的", this.updatedProfile);
      console.log(this.profileId);
      let that = this;
      try {
        const response = await axios.put("http://localhost:9090/profiles", {
          seekerId: that.seekerId,
          name: that.updatedProfile.name,
          age: that.updatedProfile.age,
          sex: that.updatedProfile.sex,
          email: that.updatedProfile.email,
          education: that.updatedProfile.education,
          phone: that.updatedProfile.phone,
          college: that.updatedProfile.college,
          major: that.updatedProfile.major,
          admissionDate: that.updatedProfile.admissionDate,
          graduationDate: that.updatedProfile.graduationDate,
          reward: that.updatedProfile.honor,
          expectedLocation: that.updatedProfile.expected_location,
          personalDescription: that.updatedProfile.description,
          id: that.profileId,
        });
        //alert(response.data.code)
        console.log("后端返回的数据", response.data.data)
        if (response.data.code === 200) {
          if (response.data.code === 200 && this.avatarToUpload !== null) {
            this.uploadAvatar(response.data.data);
          }
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
    // New method for handling the file selection
    handleAvatarChange(file) {
      this.$message.success("上传")
      this.avatarToUpload = file;
      console.log(this.avatarToUpload);
    },

    // New method for uploading the avatar
    async uploadAvatar(id) {
      console.log(id);
      let formData = new FormData();

      formData.append("profileId", id);
      formData.append("photo", this.avatarToUpload.raw);

      console.log(id);
      console.log(this.avatarToUpload.raw);

      axios.post("http://localhost:9090/profiles/upload-files", formData)
        .then(response => {
          if (response.data.code === 200) {
            this.$message.success("头像上传成功");
          } else {
            this.$message.error(response.data.msg);
          }
        }).catch(error => {
          this.$message.error("头像上传失败");
        });
    },
  },
};
</script>

  