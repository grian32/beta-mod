import type {ReactNode} from 'react';
import clsx from 'clsx';
import Heading from '@theme/Heading';
import styles from './styles.module.css';

type FeatureItem = {
    title: string;
    image?: string;
    description: ReactNode;
};

const FeatureList: FeatureItem[] = [

    {
        title: 'Item Enhancements',
        image: require('@site/static/img/enhancement_table_main.png').default,
        description: (
            <>
                Upgrade your tools with powerful effects at the Enhancement Table.
            </>
        ),
    },
    {
        title: 'Icy Dungeons',
        image: require('@site/static/img/icy_dungeon.png').default,
        description: (
            <>
                Explore rare underground dungeons hidden beneath Taiga and Tundra biomes
            </>
        ),
    },
    {
        title: 'Mysterious Shrine',
        image: require('@site/static/img/mysterious_shrine.png').default,
        description: (
            <>
                A shrine that rarely generates across the world, while it is known the shrine can be activated, the way to do so and its effects have been lost to time.
            </>
        ),
    },
];

function Feature({title, image, description}: FeatureItem) {
    return (
        <div className={clsx('col col--4')}>
            <div className="text--center">
                {image ? (
                    <img className={styles.featureImage} src={image} alt={title}/>
                ) : (
                    <div className={clsx(styles.featureImage, styles.comingSoonImage)}>
                        Coming Soon
                    </div>
                )}
            </div>
            <div className="text--center padding-horiz--md">
                <Heading as="h3">{title}</Heading>
                <p>{description}</p>
            </div>
        </div>
    );
}

export default function HomepageFeatures(): ReactNode {
    return (
        <section className={styles.features}>
            <div className={clsx('container', styles.featureContainer)}>
                <div className="row">
                    {FeatureList.map((props, idx) => (
                        <Feature key={idx} {...props} />
                    ))}
                </div>
            </div>
        </section>
    );
}
