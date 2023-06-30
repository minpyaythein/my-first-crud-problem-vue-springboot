<script setup>
import axios from 'axios';
import { useUsersStore } from '../stores/users';
import { useRouter } from 'vue-router';

const router = useRouter();

const goToUsers = () => {
    router.push("/users")
}

const usersStore = useUsersStore();

const deleteUsers = async () => {
    try {
        const response = await axios.delete(`http://localhost:8080/users/delete/${usersStore.id}`);
        console.log(response.data);
        router.push("/users");
    } catch (e) {
        console.error("Error:", e);
    }
};

</script>

<template>
    <div class="delete-h1">
        <h1>研修生 削除</h1>
        <hr>
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
        <label for="imageStyle">プロファイル:</label>
        <img :src="'../src/components/images/' + (usersStore.image || 'default.png')" alt="preview" class="roundPictures">
    </div>
    <p class="confirm-question">こちらのデータを削除します。本当によろしいでしょうか？</p>
    <div class="input-container">
        <img src="https://media.tenor.com/y-5Cz6bL8U4AAAAi/cry-floor.gif" alt="">
    </div>
    <div class="buttons">
        <button class="confirm-button" @click="deleteUsers">削除</button>
        <button class="cancel-button" @click="goToUsers">キャンセル</button>
    </div>
</template>

<style scoped>
.delete-h1 {
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

.roundPictures {
    border-radius: 50%;
    width: 50px;
    height: 50px;
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
</style>