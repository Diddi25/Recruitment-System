import { ref, onMounted } from "vue";
import { advertisementService } from "@/services/api.js";

const advertisement = ref([]);
const advertisementError = ref(null);
const advertisementLoading = ref(false);

async function fetchAdvertisements() {
  advertisementLoading.value = true;
  try {
    let result = await advertisementService.getAll();
    advertisement.value = result.data;
  } catch (err) {
    advertisementError.value = `Error fetching advertisements: ${err.message}`;
  } finally {
    advertisementLoading.value = false;
  }
}

onMounted(fetchAdvertisements);

export default {
  advertisement,
  advertisementError,
  advertisementLoading,
  fetchAdvertisements,
};

