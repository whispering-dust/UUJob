<template>
  <div style="min-height: 1080px;background-color: rgb(223, 229, 235);">
    <div class="bg-tertiary text-white px-4 px-lg-5 py-5 rounded-0 border-0 mb-0" style="height: 300px;">
      <div class="create-post container" style="height:100%">
        <h1  style="margin-left:40%">发布帖子</h1>
        <el-form :model="postForm" label-width="50px" >
          <el-form-item label="标题">
            <el-input  v-model="postForm.title" placeholder="请输入帖子标题"></el-input>
          </el-form-item>
          <el-form-item label="分类" style="width: 30%;">
            <el-select v-model="postForm.category" placeholder="请选择帖子分类">
              <el-option
                v-for="item in categories"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="标签" >
            <el-input v-model="postForm.tags" placeholder="请输入帖子标签，用逗号分隔"></el-input>
          </el-form-item> -->
          <el-form-item label="内容">
            <div><editor-toolbar  @execute="editorExecute" /></div>
            <div class="editor-wrapper" ref="editorContainer">
              <editor-content class="my-editor" :editor="editor" v-model="postForm.content" style="color:black;"/>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button color="black" @click="submitForm">发布</el-button>
            <el-button @click="resetForm">取消</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
      
  </div>
  
  </template>

<script>
import axios from 'axios'
import { ref } from 'vue'
import { ElForm, ElFormItem, ElInput, ElButton, ElSelect, ElOption } from 'element-plus'
import { Editor,useEditor, EditorContent } from '@tiptap/vue-3'
import StarterKit from '@tiptap/starter-kit'
import Bold from '@tiptap/extension-bold'
import Italic from '@tiptap/extension-italic'
import Underline from '@tiptap/extension-underline'
import Strike from '@tiptap/extension-strike'
import Heading from '@tiptap/extension-heading'
import BulletList from '@tiptap/extension-bullet-list'
import Blockquote from '@tiptap/extension-blockquote'
import Link from '@tiptap/extension-link'
import Image from '@tiptap/extension-image'
import HorizontalRule from '@tiptap/extension-horizontal-rule'
import Code from '@tiptap/extension-code'
import CodeBlock from '@tiptap/extension-code-block'
import History from '@tiptap/extension-history'
import EditorToolbar from './EditorToolbar.vue'
import {useStore} from 'vuex'

export default {
    components: {
        ElForm,
        ElFormItem,
        ElInput,
        ElButton,
        ElSelect,
        ElOption,
        EditorContent,
        EditorToolbar,
    },
    data() {
        return {
            postForm: {
                title: '',
                category: '',
                // tags: '',
                content: ''
            },
            categories: [
                { value: 1, label: '技术交流' },
                { value: 2, label: '求职经验' },
                { value: 3, label: '行业动态' },
                { value: 0, label: '其他' }
            ],
            editor: null,
            userId : useStore().state.userId,
        }
    },
    methods:{
        submitForm() {
            console.log(this.postForm)
            let that = this
            axios.post('http://localhost:9090/posts',{
              publisherId : that.userId,
              title : that.postForm.title,
              content : that.postForm.content,
              type : that.postForm.category,
              
            })
            .then(response => {
            if (response.data.code == 200) {
                this.$message.success('帖子发布成功')
                this.$router.push('/forum')
            } else {
                this.$message.error(response.data.message)
            }
            })
            .catch(error => {
            this.$message.error('发布帖子失败，请稍后重试')
            })
        },
        resetForm() {
            this.postForm = {
                title: '',
                category: '',
                // tags: '',
                content: ''
            }
        },
        async uploadImage() {
            return new Promise((resolve, reject) => {
                const input = document.createElement('input');
                input.type = 'file';
                input.accept = 'image/*';
                input.onchange = () => {
                const file = input.files[0];
                if (file) {
                    const reader = new FileReader();
                    reader.onload = (e) => {
                    resolve(e.target.result);
                    };
                    reader.onerror = (e) => {
                    reject(new Error('读取文件出错'));
                    };
                    reader.readAsDataURL(file);
                } else {
                    reject(new Error('未选择文件'));
                }
                };
                input.click();
            });
        },

        editorExecute(event) {
            const { command, options } = event;
            switch (command) {
                case 'bold':
                this.editor.chain().focus().toggleBold().run();
                break;
                case 'italic':
                this.editor.chain().focus().toggleItalic().run();
                break;
                case 'underline':
                this.editor.chain().focus().toggleUnderline().run();
                break;
                case 'strike':
                this.editor.chain().focus().toggleStrike().run();
                break;
                case 'setHeading':
                this.editor.chain().focus().setHeading(options.level).run();
                break;
                case 'toggleBulletList':
                this.editor.chain().focus().toggleBulletList().run();
                break;
                case 'toggleBlockquote':
                this.editor.chain().focus().toggleBlockquote().run();
                break;
                case 'setLink':
                this.editor.chain().focus().extendMarkRange('link').setLink(options).run();
                break;
                case 'insertImage':
                this.uploadImage()
                    .then((src) => {
                    this.editor.chain().focus().insertContent({ type: 'image', attrs: { src } }).run();
                    })
                    .catch((error) => {
                    console.error('上传图片失败', error);
                    this.$message.error('上传图片失败');
                    });
                break;
                case 'insertHorizontalRule':
                this.editor.chain().focus().insertContent({ type: 'horizontalRule' }).run();
                break;
                case 'toggleCode':
                this.editor.chain().focus().toggleCode().run();
                break;
                case 'toggleCodeBlock':
                this.editor.chain().focus().toggleCodeBlock().run();
                break;
                case 'undo':
                this.editor.chain().focus().undo().run();
                break;
                case 'redo':
                this.editor.chain().focus().redo().run();
                break;
                default:
                console.warn(`Unknown command: ${command}`);
            }
        }


    },

    mounted(){
        this.editor = new Editor({
            content:'',
            attributes: {
              class: 'ProseMirror',
            },
            extensions: [
                Bold,
                Italic,
                Underline,
                Strike,
                Heading.configure({ levels: [1, 2, 3] }),
                BulletList,
                Blockquote,
                Link,
                Image,
                HorizontalRule,
                Code,
                CodeBlock,
                StarterKit
            ],
            onUpdate: ({ editor }) => {
                this.postForm.content = editor.getHTML()
            }
        })
        //this.editor.attachTo(this.editorContainer)
    }
}
</script>

<style scoped>
.create-post {
  max-width: 800px;
  min-height: 1080px;
  margin: 0 auto;
  padding: 20px;
  background-color: #ffffff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  border-radius: 6px;
}

.el-form-item {
  margin-bottom: 20px;
}

.el-form-item__label {
  font-size: 14px;
  font-weight: 600;
  color: #333;
}

.el-input,
.el-select {
  width: 100%;
}

.el-button {
  margin-right: 10px;
}

.editor {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  padding: 10px;
  min-height: 200px;
}

.editor:focus {
  border-color: #409eff;
}

.editor p {
  margin: 0;
}
.my-editor {
  max-height: 680px; /* 设置文本区域的最大高度 */
  overflow: auto; /* 当内容超出区域时，显示滚动条 */
  border: 0px;
}
.ProseMirror .ProseMirror-focused{
  outline: none !important;
  box-shadow: none !important;
  border: none !important;
}


.editor-wrapper {
    border: 1px solid #dcdfe6;
    border-radius: 4px;
    padding: 10px;
    height: 700px;
    width: 100%;
  }
  .editor-wrapper:focus-within {
    border-color: #409eff;

  }
  .editor-wrapper p {
    margin: 0;
  }
</style>
