import { createStore } from 'vuex'
import storage from "../utils/storage"

export default createStore({
  state: {
    userId: storage.get('userId'),
    userAvatar:'',
    profileImgUrl:'',
    userName: storage.get('userName'),
    profileId: storage.get('profileId'),
    role: storage.get('role'),//0 是求职者，1 是招聘者
    pass:0,
  },
  getters: {
    getUserName(state){
      return state.userName;
    },
    getUserAvatar(state){
      return state.userAvatar;
    },
    getUserId(state){
      return state.userId;
    },
    getProfileId(state){
      return state.profileId;
    },
    getProfileImgUrl(state){
      return state.profileImgUrl;
    }
  },
  mutations: {
    setUserName(state,value){
      state.userName = value;
      storage.set('userName',value);
    },
    setUserId(state, value) {
      state.userId = value;
      storage.set('userId',value);
    },
    setProfileId(state, value) {
      state.profileId = value;
      storage.set('profileId',value);
    },
    setPass(state, value) {
      state.pass = value;
    },
    setRole(state, value) {
      state.role = value;
      storage.set('role',value);
    },
    setUserAvatar(state, value) {
      state.userAvatar = value;
    },
    setProfileImgUrl(state, value){
      state.profileImgUrl = value;
      storage.set('profileImgUrl',value);
    },

  },
  actions: {
  },
  modules: {
  }
})
