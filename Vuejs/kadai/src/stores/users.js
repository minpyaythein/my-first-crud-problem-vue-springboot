import { defineStore } from 'pinia'

export const useUsersStore = defineStore({
  id: 'users',
  state: () => ({
    id: "",
    username: "",
    email: "",
    image: ""
  }),
  actions: {
    selectedUsers(id, username, email) {
      this.id = id;
      this.username = username;
      this.email = email;

      console.log("users referred");
    },
    selectedUsers2(id, username, email, image) {
      this.id = id;
      this.username = username;
      this.email = email;
      this.image = image;

      console.log("users2 referred");
    },
    selectedUsers3(username, email, image) {
      //this.id = id;
      this.username = username;
      this.email = email;
      this.image = image;

      console.log("users2 referred");
    }
  },
  getters: {

  }
})
