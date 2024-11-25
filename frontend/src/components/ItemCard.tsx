import {Item} from "../models/Item.ts";
import "./ItemCard.css";

type Props = {
    item: Item;
}


export default function ItemCard(props: Props) {
    return (
        <div className="itemCard-container">
            <p>{props.item.img}</p>
            <p>{props.item.name}</p>
        </div>
    );
};