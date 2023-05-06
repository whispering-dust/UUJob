<template>
    <div class="post-main pt-2">
        <!-- <div class="card-bk bg-tertiary text-white px-4 px-lg-5 py-5 rounded-0 border-0 mb-0"> -->
           <div class="container post-area">
                <PostCard></PostCard>
                <div> <Comment></Comment></div>
           </div>
        <!-- </div> -->
        
    </div>

</template>

<script>
import PostCard from '@/components/forum/PostCard.vue'
import Comment from '@/components/forum/Comment.vue'
export default{
    components:{
        PostCard,
        Comment,
    },
    data(){
        
    }
}

</script>

<style scoped>
.post-main {
    display: flex;
    flex-direction: column;
    min-height: 800px;
    background-color: rgb(223, 229, 235);
}
  
.card-bk {
    height: 300px;
    display: flex;
    flex-direction: column;
}
.post-area{
    display: flex;
    flex-direction: column;
}
</style>