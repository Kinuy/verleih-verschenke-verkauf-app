import {Item} from "../models/Item.ts";
import "./ItemCard.css";

type Props = {
    item: Item;
}


export default function ItemCard(props: Props) {
    return (
        <div className="itemCard-container">
            <div className="itemCard-tags">
                <p>{props.item.name}</p>
            </div>
        </div>
    );
};