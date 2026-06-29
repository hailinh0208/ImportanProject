import React from 'react';
import { createRoot, type Root } from 'react-dom/client';
import { fragmentsMappings } from './liferay/sample-fragments/mappings';
import configurations from './liferay/sample-fragments/configurations.json';

const toCamelCase = (str: string): string =>
    str.replace(/-([a-z])/g, (_, c: string) => c.toUpperCase());

configurations.forEach(({ name }) => {
    const Component = fragmentsMappings[name];
    if (!Component || customElements.get(name)) return;

    customElements.define(
        name,
        class extends HTMLElement {
            private root: Root | null = null;

            connectedCallback() {
                const props = Object.fromEntries(
                    this.getAttributeNames().map((attr) => [
                        toCamelCase(attr),
                        this.getAttribute(attr) ?? '',
                    ])
                );

                this.root = createRoot(this);
                this.root.render(React.createElement(Component, props));
            }

            disconnectedCallback() {
                this.root?.unmount();
                this.root = null;
            }
        }
    );
});
