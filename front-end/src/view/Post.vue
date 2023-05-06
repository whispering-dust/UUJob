<template>
    <div class="post-main">
        <div class="card-bk bg-tertiary text-white px-4 px-lg-5 py-5 rounded-0 border-0 mb-0">
           <div class="container">
                <PostCard></PostCard>
                <Comment></Comment>
           </div>
        </div>
    </div>

</template>

<script>
import PostCard from '@/components/forum/PostCard.vue'
import Comment from '@/components/forum/Comment.vue'
export default{
    components:{
        PostCard,
        Comment,
    }
}

</script>

<style scoped>
.post-main {
    min-height: 800px;
    background-color: rgb(223, 229, 235);
    overflow: auto;
}
  
.card-bk {
    height: 300px;
}
</style>