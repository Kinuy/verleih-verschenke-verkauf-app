
import "./models/ItemCategory.ts";
import NewItemCard from "./components/NewItemCard.tsx";
import ItemGallery from "./components/ItemGallery.tsx";
import "./App.css";
import Header from "./components/Header.tsx";
import {Route, Routes} from "react-router";
import Home from "./components/Home.tsx";
import {useState} from "react";
import {Item} from "./models/Item.ts";
import ItemDetails from "./components/ItemDetails.tsx";
import Account from "./components/Account.tsx";
import AccountLogin from "./components/AccountLogin.tsx";
import AccountCreate from "./components/AccountCreate.tsx";
import ProtectedRoute from "./components/ProtectedRoute.tsx";
import {AppUser} from "./models/AppUser.ts";
import axiosAccess from "./utility/access.ts";



export default function App() {

    const [user, setUser] = useState<AppUser>()
    const [items, setItems] = useState<Item[]>([])

    //document.title = "StuffLoop";


    function fetchAllItems(){
        axiosAccess.get("/api/item")
            .then((response)=>{
                setItems(response.data);
            })
            .catch(error => {console.error("Error fetching item list:", error);
            });
    }

    function updateUser(appUser: AppUser){
        setUser(appUser)
    }

    return (
        <div>
            <Header/>
            <Routes>
                <Route path={"/"} element={<Home user={user}/>}/>
                <Route path={"/item/:id"} element={<ItemDetails />}/>
                <Route path={"/storage"} element={<ItemGallery items={items}/>}/>
                <Route path={"/account"} element={<Account updateUser={updateUser}/>}/>
                <Route path={"/account/login"} element={<AccountLogin updateUser={updateUser}/>}/>
                <Route path={"/account/create"} element={<AccountCreate/>}/>
                <Route path={"*"} element={<p>Page not found!</p>}/>

                <Route element={<ProtectedRoute user={user} />}>
                    <Route path={"/manage"} element={<NewItemCard updateList={fetchAllItems} items={items} user={user}/>}/>
                </Route>
            </Routes>
        </div>
    )
}
