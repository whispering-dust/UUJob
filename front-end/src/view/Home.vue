<template>
  <section class="spotlight parallax bg-cover bg-size--cover" data-spotlight="fullscreen" style="background-image: url('../assets/images/backgrounds/img-3.jpg')">
    <span class="mask bg-dark alpha-7">
      <div class="container">
        <div class="row">
          <div class="col-md-8" >
            <section class="container" >             
              <RecruitArea></RecruitArea>
            </section>
            
          </div>
          <div class="col-md-4">
            <FunctionArea></FunctionArea>  
          </div>
        </div>
      </div>
    </span>
    
  </section>
    

</template>

<script lang="ts">
import  RecruitArea   from "@/components/home/RecruitArea.vue";
import FunctionArea from "@/components/home/FunctionArea.vue";

export default{
    name:"Home",
    components:{
      RecruitArea,
      FunctionArea,
    },

};
</script>

<style>
.home{
    background-color: rgb(245, 245, 245);
    width: 100%;
    height: 100%;
    left: 0;
    min-height: 800px;
}
.main{
  width: 100%;
  height: 100%;
}

.recruit{
  float: left;
  position: relative;
  left: 20%;
}

.function{
  float: right;
  position: relative;
  right: 8%;
}


.el-row {
    margin-bottom: 20px;
  }
  .el-row:last-child {
    margin-bottom: 0;
  }
  .el-col {
    border-radius: 4px;
  }
  
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }

</style>