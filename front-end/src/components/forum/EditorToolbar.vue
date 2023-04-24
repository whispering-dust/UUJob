<template>
    <div class="execute-toolbar">
      <el-button-group>
        <el-button @click="$emit('execute', { command: 'bold' })"><font-awesome-icon icon="bold" ></font-awesome-icon></el-button>
        <el-button @click="$emit('execute', { command: 'italic' })"><font-awesome-icon icon="italic" ></font-awesome-icon></el-button>
        <el-button @click="$emit('execute', { command: 'underline' })"><font-awesome-icon icon="underline" ></font-awesome-icon></el-button>
        <el-button @click="$emit('execute', { command: 'strike' })"><font-awesome-icon icon="strikethrough" ></font-awesome-icon></el-button>
        <el-button @click="$emit('execute', { command: 'setHeading', options: { level: 1 } })">H1</el-button>
        <el-button @click="$emit('execute', { command: 'setHeading', options: { level: 2 } })">H2</el-button>
        <el-button @click="$emit('execute', { command: 'setHeading', options: { level: 3 } })">H3</el-button>
        <el-button @click="$emit('execute', { command: 'toggleBulletList' })"><font-awesome-icon icon="list-ul" ></font-awesome-icon></el-button>
        <el-button @click="$emit('execute', { command: 'toggleBlockquote' })"><font-awesome-icon icon="quote-left" ></font-awesome-icon></el-button>
        <el-button @click="$emit('execute', { command: 'setLink', options: { href: 'https://example.com' } })"><font-awesome-icon icon="link" ></font-awesome-icon></el-button>
        <el-button @click="$emit('execute', { command: 'insertImage', options: { src: 'https://example.com/image.jpg' } })"><font-awesome-icon icon="image" ></font-awesome-icon></el-button>
        <el-button @click="$emit('execute', { command: 'insertHorizontalRule' })"><font-awesome-icon icon="minus" ></font-awesome-icon></el-button>
        <el-button @click="$emit('execute', { command: 'toggleCode' })"><font-awesome-icon icon="code" ></font-awesome-icon></el-button>
        <el-button @click="$emit('execute', { command: 'toggleCodeBlock' })"><font-awesome-icon icon="file-code" ></font-awesome-icon></el-button>
        <el-button @click="$emit('execute', { command: 'undo' })"><font-awesome-icon icon="undo" ></font-awesome-icon></el-button>
        <el-button @click="$emit('execute', { command: 'redo' })"><font-awesome-icon icon="redo" ></font-awesome-icon></el-button>
    </el-button-group>
  </div>
</template>

<script>
export default {
  name: 'executeToolbar',
  props: {
    // editor: {
    //   type: Function,
    //   required: true,
    // },
  },
};
</script>

<style scoped>
.execute-toolbar {
  display: flex;
  justify-content: center;
  background-color: #f9f9f9;
  padding: 10px;
  border-bottom: 1px solid #e8e8e8;
}

.el-button-group .el-button {
  padding: 0;
  width: 32px;
  height: 32px;
}
</style>

  