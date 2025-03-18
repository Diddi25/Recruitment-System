import { createStore } from "vuex";
import { auth } from "./authModule";

/*
* The store is used to store user/authentication information in a Vue store.
*/
const store = createStore({
  modules: {
    auth,
  },
});

export default store;