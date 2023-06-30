<script setup>
import axios from 'axios';
import { useUsersStore } from '../stores/users';
import { useRouter } from 'vue-router';

const router = useRouter();
const usersStore = useUsersStore();

const updateUsers = async () => {
    const users = {
        name: usersStore.username,
        email: usersStore.email,
        image: usersStore.image
    }
    try {
        // await axios.post(`http://localhost:8080/users/update/${usersStore.id}?name=${usersStore.username}&email=${usersStore.email}`);
        await axios.post(`http://localhost:8080/users/update/${usersStore.id}`, users);
        router.push("/users");
    } catch (e) {
        console.error("Error:", e);
    }
};

const goToUsers = () => {
    router.push("/users");
}
</script>

<template>
    <div class="update-confirm-h1">
        <h1>研修生 編集 確認</h1>
        <hr>
    </div>
    <p class="confirm-question">下記の情報に編集します。よろしいでしょうか？</p>
    <div class="input-container">
        <label for="idStyle">ID:</label>
        <p id="idStyle">{{ usersStore.id }}</p>
    </div>
    <div class="input-container">
        <label for="usernameStyle">名前:</label>
        <p id="usernameStyle">{{ usersStore.username }}</p>
    </div>
    <div class="input-container">
        <label for="emailStyle">メールアドレス:</label>
        <p id="emailStyle">{{ usersStore.email }}</p>
    </div>
    <div class="input-container">
        <label for="emailStyle">プロファイル:</label>
        <img :src="'../src/components/images/' + (usersStore.image || 'default.png')" alt="preview" class="roundPictures">
    </div>
    <div class="buttons">jishakaihatsu
        <button class="confirm-button" @click="updateUsers">更新</button>
        <button class="cancel-button" @click="goToUsers">キャンセル</button>
    </div>
</template>

<style scoped>
.update-confirm-h1 {
    text-align: center;
}

.confirm-question {
    text-align: center;
}

.input-container {
    display: flex;
    justify-content: center;
    margin-top: 50px;
}

#idStyle {
    margin-left: 10px;
    margin-top: 0px;
    padding: 3px;
    width: 200px;
    height: 20px;
}

#usernameStyle,
#emailStyle {
    margin-left: 10px;
    margin-top: 0px;
    border: 1px solid #ccc;
    padding: 3px;
    width: 200px;
    height: 20px;
}

.buttons {
    text-align: center;
    margin-top: 20px;
}

.cancel-button {
    margin-left: 25px;
}

.roundPictures {
    border-radius: 50%;
    width: 50px;
    height: 50px;
}
</style>