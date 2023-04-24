<template>
    <div class="editor" v-if="editor" :style="{width}">
        <editor-content class="editor-content" :editor="editor" />
    </div>
</template>

<script>
    import { defineComponent } from 'vue'
    import { useEditor, EditorContent } from '@tiptap/vue-3'
    import StarterKit from '@tiptap/starter-kit'
    export default defineComponent({
      components: {
        EditorContent
      },
      props: {
        width: {
          type: String,
          default: '800px'
        }
      },
      setup() {
        const editor = useEditor({
          content: '<p>I’m running Tiptap with vue-next. 🎉</p>',
          extensions: [
            StarterKit
          ]
        })
        return {
          editor
        }
      }
    })
</script>