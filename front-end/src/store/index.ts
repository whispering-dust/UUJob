import { createStore } from 'vuex'

export default createStore({
  state: {
    userId: "17b102d5-13cc-4b58-8665-30fad179dbc2",
    profileId:" ",
    role:0,//0 是求职者，1 是招聘者
    pass:0,
  },
  getters: {
  },
  mutations: {
    set(state, value) {
      state.userId = value;
    },
    setProfile(state, value) {
      state.profileId = value;
    },
    setPass(state, value) {
      state.pass = value;
    },
    setRole(state, value) {
      state.role = value;
    }
  },
  actions: {
  },
  modules: {
  }
})
