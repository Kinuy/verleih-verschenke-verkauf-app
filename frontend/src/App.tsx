
import "./models/ItemCategory.ts";
import NewItemCard from "./components/NewItemCard.tsx";
import ItemGallery from "./components/ItemGallery.tsx";
import "./App.css";
import Header from "./components/Header.tsx";
import {Route, Routes} from "react-router";
import Home from "./components/Home.tsx";



export default function App() {
    return (
        <div>
            <Header/>
            <Routes>
                <Route path={"/"} element={<Home/>}/>
                <Route path={"/manage"} element={<NewItemCard/>}/>
                <Route path={"/storage"} element={<ItemGallery/>}/>
                <Route path={"*"} element={<p>Page not found!</p>}/>
            </Routes>
        </div>
    )
}
