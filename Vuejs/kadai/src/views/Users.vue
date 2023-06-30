<script setup>
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useUsersStore } from '../stores/users';
import { onMounted, ref, computed } from 'vue';

const usersStore = useUsersStore();
const router = useRouter();

const goToCreate = () => {
  router.push("/create")
}

const goToDelete = (id, username, email, image) => {
  try {
    usersStore.selectedUsers2(id, username, email, image); // Store the selected user data in the store
    router.push("/delete");
  } catch (e) {
    console.error("Error", e);
  }
};

const updateUser = (id, username, email, image) => {
  try {
    usersStore.selectedUsers2(id, username, email, image);
    router.push("/update")
  } catch (e) {
    console.error("Error", e);
  }
};

const users = ref(null);
const usersPerPage = 4;
const currentPage = ref(1);
const totalPages = computed(() => {
  return Math.ceil(users.value.length / usersPerPage);
});

onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8080/users');
    users.value = response.data;
  } catch (e) {
    console.error("Network Error", e);
  }
});

const getCurrentPageUsers = computed(() => {
  const startIndex = (currentPage.value - 1) * usersPerPage;
  const endIndex = startIndex + usersPerPage;
  return users.value.slice(startIndex, endIndex);
});

const goToNextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
  }
};

const goToPreviousPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
  }
};

const goToPage = (page) => {
  currentPage.value = page;
};
</script>

<template>
  <main>

  </main>

  <body>
    <div v-if="users">
      <div class="users-h1">
        <h1>研修生一覧</h1>
      </div>
      <hr>
      <div>
        <button class="plus-button" @click="goToCreate">+</button>
      </div>
      <table>
        <thead>
          <tr>
            <th>id</th>
            <th>name</th>
            <th>email</th>
            <th>image</th>
            <th>編集</th>
            <th>削除</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in getCurrentPageUsers" :key="user.id">
            <td class="onlyTextAndIcons">{{ user.id }}</td>
            <td class="onlyTextAndIcons">{{ user.name }}</td>
            <td class="onlyTextAndIcons">{{ user.email }}</td>
            <td>
              <div class="image-container">
                <img :src="'../src/components/images/' + (user.image || 'default.png')" alt="" class="roundPictures">
              </div>
            </td>
            <td class="onlyTextAndIcons"><button
                @click="updateUser(user.id, user.name, user.email, user.image)">✏️</button>
            </td>
            <td class="onlyTextAndIcons"><button
                @click="goToDelete(user.id, user.name, user.email, user.image)">🗑</button>
            </td>
          </tr>
        </tbody>
      </table>
      <div class="pagination">
        <button :disabled="currentPage === 1" @click="goToPreviousPage">Previous</button>
        <span v-if="totalPages > 1">
          <template v-if="totalPages === 4">
            <template v-if="currentPage < 2">
              <template v-for="page in Math.min(totalPages, 2)">
                <span class="current-page" v-if="page === currentPage">{{ page }}</span>
                <span v-else>
                  <button @click="goToPage(page)">{{ page }}</button>
                </span>
                <span v-if="page !== totalPages">, </span>
              </template>
              <span v-if="totalPages > 3"> ... , <button @click="goToPage(totalPages)">{{ totalPages }}</button></span>
            </template>
            <template v-else>
              <template v-for="page in Math.min(totalPages, 4)">
                <span class="current-page" v-if="page === currentPage">{{ page }}</span>
                <span v-else>
                  <button @click="goToPage(page)">{{ page }}</button>
                </span>
                <span v-if="page !== totalPages">, </span>
              </template>
            </template>
          </template>
          <template v-if="totalPages === 5">
            <template v-if="currentPage === 1">
              <template v-for="page in [currentPage, currentPage + 1]">
                <span class="current-page" v-if="page === currentPage">{{ page }}</span>
                <span v-else>
                  <button @click="goToPage(page)">{{ page }}</button>
                </span>
                <span v-if="page !== totalPages">, </span>
              </template>
              <span v-if="totalPages > 3"> ... , <button @click="goToPage(totalPages)">{{ totalPages }}</button></span>
            </template>
            <template v-else-if="currentPage === 2">
              <template v-for="page in [currentPage - 1, currentPage, currentPage + 1]">
                <span class="current-page" v-if="page === currentPage">{{ page }}</span>
                <span v-else>
                  <button @click="goToPage(page)">{{ page }}</button>
                </span>
                <span v-if="page !== totalPages">, </span>
              </template>
              <span v-if="totalPages > 3"> ... , <button @click="goToPage(totalPages)">{{ totalPages }}</button></span>
            </template>
            <template v-else-if="currentPage === 4">
              <span><button @click="goToPage(1)">1</button>, ... </span>
              <template v-for="page in [currentPage - 1, currentPage, currentPage + 1]">
                <span class="current-page" v-if="page === currentPage">{{ page }}</span>
                <span v-else>
                  <button @click="goToPage(page)">{{ page }}</button>
                </span>
                <span v-if="page !== totalPages">, </span>
              </template>
            </template>
            <template v-else-if="currentPage === 5">
              <span><button @click="goToPage(1)">1</button>, ... </span>
              <template v-for="page in [totalPages - 1, totalPages]">
                <span class="current-page" v-if="page === currentPage">{{ page }}</span>
                <span v-else>
                  <button @click="goToPage(page)">{{ page }}</button>
                </span>
                <span v-if="page !== totalPages">, </span>
              </template>
            </template>
            <template v-else>
              <span><button @click="goToPage(1)">1</button>, </span>
              <template v-for="page in [currentPage - 1, currentPage, currentPage + 1]">
                <span class="current-page" v-if="page === currentPage">{{ page }}</span>
                <span v-else>
                  <button @click="goToPage(page)">{{ page }}</button>
                </span>
                <span v-if="page !== totalPages">, </span>
              </template>
              <span><button @click="goToPage(totalPages)">{{ totalPages }}</button></span>
            </template>
          </template>
          <template v-else>
            <template v-if="currentPage < 3">
              <!-- Show page numbers 1, 2, 3, ..., (last page number) -->
              <template v-for="page in Math.min(totalPages, 3)">
                <span class="current-page" v-if="page === currentPage">{{ page }}</span>
                <span v-else>
                  <button @click="goToPage(page)">{{ page }}</button>
                </span>
                <span v-if="page !== totalPages">, </span>
              </template>
              <span v-if="totalPages > 3"> ... , <button @click="goToPage(totalPages)">{{ totalPages
              }}</button></span>
            </template>
            <template v-else-if="currentPage === 3">
              <!-- Show page numbers 1, 2, 3, 4, ..., (last page number) -->
              <template v-for="page in Math.min(totalPages, 4)">
                <span class="current-page" v-if="page === currentPage">{{ page }}</span>
                <span v-else>
                  <button @click="goToPage(page)">{{ page }}</button>
                </span>
                <span v-if="page !== totalPages">, </span>
              </template>
              <span v-if="totalPages > 3"> ... , <button @click="goToPage(totalPages)">{{ totalPages
              }}</button></span>
            </template>
            <template v-else-if="currentPage > totalPages - 2">
              <!-- Show page numbers ..., (last page number - 2), (last page number - 1), last page number -->
              <span><button @click="goToPage(1)">1</button>, ... </span>
              <template v-for="page in [totalPages - 2, totalPages - 1, totalPages]">
                <span class="current-page" v-if="page === currentPage">{{ page }}</span>
                <span v-else>
                  <button @click="goToPage(page)">{{ page }}</button>
                </span>
                <span v-if="page !== totalPages">, </span>
              </template>
            </template>
            <template v-else-if="currentPage === totalPages - 2">
              <!-- Show page numbers first page number, ... , (last page number - 3), (last page number - 2), (last page number - 1), last page number -->
              <span><button @click="goToPage(1)">1</button>, ... </span>
              <template v-for="page in [totalPages - 3, totalPages - 2, totalPages - 1, totalPages]">
                <span class="current-page" v-if="page === currentPage">{{ page }}</span>
                <span v-else>
                  <button @click="goToPage(page)">{{ page }}</button>
                </span>
                <span v-if="page !== totalPages">, </span>
              </template>
            </template>
            <template v-else>
              <!-- Show page numbers ..., (current page - 1), current page, (current page + 1), ... -->
              <span><button @click="goToPage(1)">1</button>, ... , </span>
              <template v-for="page in [currentPage - 1, currentPage, currentPage + 1]">
                <span class="current-page" v-if="page === currentPage">{{ page }}</span>
                <span v-else>
                  <button @click="goToPage(page)">{{ page }}</button>
                </span>
                <span v-if="page !== totalPages">, </span>
              </template>
              <span> ... , <button @click="goToPage(totalPages)">{{ totalPages }}</button></span>
            </template>
          </template>
        </span>
        <button :disabled="currentPage === totalPages" @click="goToNextPage">Next</button>
      </div>
    </div>
  </body>
</template>

<style scoped>
.users-h1 {
  text-align: center;
}

table {
  width: 100%;
}

.onlyTextAndIcons {
  height: 20px;
  color: white;
  background: black;
  text-align: center;
  vertical-align: center;
  border: 1px #2b2b2b solid;
  position: relative;
}

.delete-icon,
.edit-icon {
  /* Define the styles for the icons */
  /* Example styles, customize as per your needs */
  display: inline-block;
  padding: 2px 5px;
  color: #fff;
  background-color: #f00;
  cursor: pointer;
}

.delete-icon::before {
  content: 'X';
}

.edit-icon::before {
  content: '✎';
}

button .buttond {
  padding: 5px 10px;
  background-color: #f00;
  color: #fff;
  border: none;
  cursor: pointer;
}

.plus-button {
  position: fixed;
  top: 20px;
  right: 20px;
  width: 40px;
  height: 40px;
  background-color: blue;
  border: none;
  border-radius: 50%;
  color: white;
  font-size: 24px;
  cursor: pointer;
  margin: 0px;
}

.pagination {
  text-align: center;
  margin-top: 10px;
  margin: 0 5px;
}

.image-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.roundPictures {
  border-radius: 50%;
  width: 50px;
  height: 50px;
}

.current-page {
  display: inline-block;
  padding: 3px 8px;
  background-color: black;
  color: #fff;
  border-radius: 3px;
}

.plus-button:hover {
  background-color: darkblue;
}
</style>