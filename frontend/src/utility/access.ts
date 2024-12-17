import axios from "axios";


const response = await axios.get("/api/users/csrf")




const axiosAccess = axios.create({headers:{[response.data.headerName] : response.data.token}})
export default axiosAccess;