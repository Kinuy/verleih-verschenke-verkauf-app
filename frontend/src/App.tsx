
import "./models/ItemCategory.ts";
import NewItemCard from "./components/NewItemCard.tsx";
import ItemGallery from "./components/ItemGallery.tsx";
import "./App.css";



export default function App() {



    return (
        <div>
            <h1>Fuer Umme / Im Keller wirds wieder heller....App</h1>
            <div className="landing-container">
                <NewItemCard/>
                <ItemGallery/>
            </div>

        </div>
    )
}
