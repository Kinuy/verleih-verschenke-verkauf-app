//import './App.css'

import axios from "axios";
import {Item} from "./models/Item.ts";
import "./models/ItemCategory.ts";


export default function App() {


    function addItem() {
        axios.post("api/item", {
            name: "Hammer",
            img: "url/hammer",
            description: "Hammer aus meinem Keller",
            category: "TOOL",
            status: "TO_LEND"
        } as Item).then();

    }

    return (
        <div>
            <h1>Landing Page</h1>
            <form onSubmit={addItem}>
                <button>add</button>
            </form>
        </div>
    )
}
