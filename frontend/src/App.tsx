
import "./models/ItemCategory.ts";
import NewItemCard from "./components/NewItemCard.tsx";
import ItemGallery from "./components/ItemGallery.tsx";
import "./App.css";
import Header from "./components/Header.tsx";
import {Route, Routes} from "react-router";
import Home from "./components/Home.tsx";
import axios from "axios";
import {useState} from "react";
import {Item} from "./models/Item.ts";
import ItemDetails from "./components/ItemDetails.tsx";



export default function App() {

    const [items, setItems] = useState<Item[]>([])

    document.title = "StuffLoop";


    function fetchAllItems(){
        axios.get("/api/item")
            .then((response)=>{
                setItems(response.data);
            })
            .catch(error => {console.error("Error fetching item list:", error);
            });
    }

    return (
        <div>
            <Header/>
            <Routes>
                <Route path={"/"} element={<Home/>}/>
                <Route path={"/item/:id"} element={<ItemDetails />}/>
                <Route path={"/manage"} element={<NewItemCard updateList={fetchAllItems} items={items}/>}/>
                <Route path={"/storage"} element={<ItemGallery items={items}/>}/>
                <Route path={"*"} element={<p>Page not found!</p>}/>
            </Routes>
        </div>
    )
}
