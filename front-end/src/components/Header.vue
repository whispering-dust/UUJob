<template>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark py-2">
        <img src="https://pic2.imgdb.cn/item/645b2b550d2dde5777379fbd.png" style="height: 60px;" class="img-fluid animated" data-animation-in="jackInTheBox"
            data-animation-delay="1000" alt="UUjob">
        <a class="navbar-brand" href="#">UUJOB</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar_example_1"
            aria-controls="navbar_example_1" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbar_example_1">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <router-link class="nav-link " to="/home">首页/求职</router-link>
                </li>
                <li class="nav-item">
                    <a class="nav-link " href="/forum">交流论坛</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link" href="/myspace">个人中心</a>
                </li>

            </ul>
            <ul class="navbar-nav ml-auto">

                <li class="nav-item">
                    <a class="nav-link nav-link-icon" href="#"><el-icon style="font-size: 25px;">
                            <Setting />
                        </el-icon></a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link nav-link-icon" href="#" id="navbar_1_dropdown_2" role="button" data-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false"><el-icon style="font-size: 25px;">
                            <Bell />
                        </el-icon></a>
                    <div class="dropdown-menu dropdown-menu-right dropdown-menu-xl py-0">
                        <div class="py-3 px-3">
                            <h5 class="heading h6 mb-0">Notifications</h5>
                        </div>
                        <div class="list-group">
                            <a href="#" class="list-group-item list-group-item-action d-flex align-items-center">
                                <div class="list-group-img">
                                    <span class="avatar bg-purple">JD</span>
                                </div>
                                <div class="list-group-content">
                                    <div class="list-group-heading">Johnyy Depp <small>10:05 PM</small></div>
                                    <p class="text-sm">Lorem ipsum dolor sit amet consectetur adipiscing eiusmod tempor</p>
                                </div>
                            </a>
                            <a href="#" class="list-group-item list-group-item-action d-flex align-items-center">
                                <div class="list-group-img">
                                    <span class="avatar bg-pink">TC</span>
                                </div>
                                <div class="list-group-content">
                                    <div class="list-group-heading">Tom Cruise <small>10:05 PM</small></div>
                                    <p class="text-sm">Lorem ipsum dolor sit amet consectetur adipiscing eiusmod tempor</p>
                                </div>
                            </a>
                            <a href="#" class="list-group-item list-group-item-action d-flex align-items-center">
                                <div class="list-group-img">
                                    <span class="avatar bg-blue">WS</span>
                                </div>
                                <div class="list-group-content">
                                    <div class="list-group-heading">Will Smith <small>10:05 PM</small></div>
                                    <p class="text-sm">Lorem ipsum dolor sit amet consectetur adipiscing eiusmod tempor</p>
                                </div>
                            </a>
                        </div>
                        <div class="py-3 text-center">
                            <a href="#" class="link link-sm link--style-3">View all notifications</a>
                        </div>
                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link nav-link-icon" href="#" id="navbar_1_dropdown_3" role="button" data-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false">
                        <div class="block">
                            <el-avatar :size="25" />
                        </div>

                    </a>
                    <div class="dropdown-menu dropdown-menu-right">
                        <h6 class="dropdown-header">用户：{{ store.state.userName }}</h6>
                        <a class="dropdown-item" href="/login">
                            <i class="fas fa-sign-out-alt text-primary"></i>注销
                        </a>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
</template>
<script>
import { useStore } from 'vuex'
import { Search, Bell, Setting } from '@element-plus/icons-vue'
export default {
    data() {
        return {
            store: useStore(),
            userName: useStore().state.userName
        }
    },
}
</script>

<style></style>