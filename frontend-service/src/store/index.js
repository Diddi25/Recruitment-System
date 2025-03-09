import { createStore } from 'vuex';
import { auth } from './authModule';  // importera auth-modulen

const store = createStore({
  modules: {
    auth,  // Lägg till auth-modulen här
  },
});

export default store;
